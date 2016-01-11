package com.company;

import exceptions.WrongDataFormatException;
import gui.DrawPanel;
import gui.MainForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Bartosz on 2014-11-23.
 */
public class Controller {

    private List<City> cities = null;
    private PackageStorage packages = null;
    private PackageStorage tmpStorage = null;
    private int carsAmount, carCapacity;
    private List<Car> availableCars = new ArrayList<Car>();
    private List<Car> carsOnTheRoute = new ArrayList<Car>();
    RouteFinder routeFinder = null;
    private int carId = 0;
    boolean packageMoved = false;
    private int startCityId;
    private long startTime;
    private float divisor = 1000F;

    private DrawPanel drawPanel;

    public void run() {

        loadData();
        assignPackagesToCitiesAtStart();
        routeFinder = new RouteFinder(cities);

        MainForm form = new MainForm();
        drawPanel = form.getDrawPanel();
        drawPanel.setStartCityId(startCityId);
        drawPanel.setCarsAmount(carsAmount);
        drawPanel.setAvailableCars(availableCars);
        drawPanel.setCarsOnTheRoute(carsOnTheRoute);
        drawPanel.setCities(cities);
        drawPanel.run();

        sleep(3000);
        startTime = System.currentTimeMillis();

        assignPackages();
    }

    private void loadData() {
        Reader r = new Reader();

        String citiesPath = "dane/miasta.txt";
        String associationsPath = "dane/polaczenia.txt";
        String packagesPath = "dane/zlecenia3.txt";

//        String citiesPath = r.readFromConsole("Podaj ścieżkę do pliku z miastami: ");
//        String associationsPath = r.readFromConsole("Podaj ścieżkę do pliku opisującego mapę: ");
//        String packagesPath = r.readFromConsole("Podaj ścieżkę do pliku z paczkami: ");

        try {
            cities = r.loadCities(citiesPath);
        } catch (WrongDataFormatException e) {
            System.err.println(e.getMessage());
        }
        r.loadAssociations(associationsPath, cities);

        startCityId = r.getStartCityId(associationsPath);

        packages = r.loadPackages(packagesPath, cities);

//        carsAmount = Integer.parseInt(r.readFromConsole("Podaj ilość dostępnych samochodów: "));
//        carCapacity = Integer.parseInt(r.readFromConsole("Podaj maksymalną ilość paczek mieszczącą się w samochodzie: "));
        carsAmount = 3;
        carCapacity = 3;
    }

    private void assignPackagesToCitiesAtStart() {
        for (Package p : packages.getPackages())
            p.getStartCity().getPackages().add(p);
    }

    private void assignPackages() {

        for (int i = 0; i < carsAmount; i++) {
            availableCars.add(new Car(i, carCapacity));
        }

        Car theCar;
        Package thePackage;
        Route theRoute;
        carId = 0;
        tmpStorage = new PackageStorage();

        while ((!packages.isEmpty())) {                                           // dopóki są gdzieś paczki
            carId = 0;
            if (!availableCars.isEmpty()) {
                theCar = availableCars.get(0);
                thePackage = packages.pop();
                theRoute = routeFinder.findRoute(thePackage.getStartCity(), thePackage.getDestinationCity());
                packageMoved = false;
                while (!thePackage.isInCar && !packageMoved) {
                    if (!theCar.isFull() && theCar.isOnRoute(theRoute)) {

                        if (theCar.getRoute() == null) {
                            theCar.setRoute(theRoute);
                            theCar.addDistance(theRoute.getCost());
                        }
                        addPackageToTheCar(theCar, thePackage);
                        drawPanel.repaint();
                        sleep(3000);
                        if (theCar.isFull()) {
                            send(theCar);
                        }
                    } else {
                        carId++;
                        if (carId < availableCars.size()) {
                            theCar = availableCars.get(carId);
                        } else {
                            moveToTmpStorage(thePackage);   //paczka nie znalazła swojego samochodu
                            packageMoved = true;
                        }
                    }
                }
            }
            if (packages.isEmpty()) {                  //jeśli już nie ma paczek w głownym kopcu to wyślij wszystkie auta
                while (!availableCars.isEmpty()) {
                    for (int i = 0; i < availableCars.size(); i++) {
                        send(availableCars.get(0));
                    }
                }
            }
            if (availableCars.isEmpty() || packages.isEmpty()) {
                // jeśli jeszcze gdzieś są paczki, ale po wysłaniu wszystkich samochodów
                sort(carsOnTheRoute);
                availableCars.add(carsOnTheRoute.get(0));
                carsOnTheRoute.get(0).setPackagesAmount(0);
                carsOnTheRoute.get(0).setRoute(null);
                carsOnTheRoute.remove(0);

                copyPackageStorage(tmpStorage, packages);
            }


        }

        System.out.println("KONIEC");

    }

    private void send(Car theCar) {
        carsOnTheRoute.add(theCar);
        availableCars.remove(theCar);

        for (int i = theCar.getPackages().size() - 1; i >= 0; i--) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("\t" + elapsedTime / divisor + " Dostarczono przesyłkę " + theCar.getPackages().get(i).getId() + " o priorytecie " + theCar.getPackages().get(i).getPriority() + " z samochodu o id " + theCar.getId() + " do miasta " + theCar.getPackages().get(i).getDestinationCity().getName());

            theCar.getPackages().get(i).getDestinationCity().getPackages().add(theCar.getPackages().get(i));
            theCar.getPackages().get(i).getStartCity().getPackages().remove(theCar.getPackages().get(i));

            drawPanel.repaint();
            sleep(3000);
        }

        theCar.setPackages(new ArrayList<Package>());
    }

    private void addPackageToTheCar(Car theCar, Package thePackage) {
        theCar.addPackage(thePackage);
        thePackage.isInCar = true;
        thePackage.setCarId(theCar.getId());

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println(elapsedTime / divisor + " Pobrano przesyłkę " + thePackage.getId() + " o priorytecie " + thePackage.getPriority() + " do samochodu o id " + theCar.getId() + " z miasta " + findCityById(startCityId).getName());
    }

    private void moveToTmpStorage(Package p) {
        tmpStorage.push(p);
    }

    private void copyPackageStorage(PackageStorage source, PackageStorage destination) {
        while (!source.isEmpty())
            destination.push(source.pop());
    }

    private void sort(List<Car> cars) {
        Collections.sort(cars, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return car1.getDistance() - car2.getDistance();
            }
        });
    }

    private City findCityById(int id) {
        for (City city : cities) {
            if (city.getId() == id)
                return city;
        }

        return null;
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
