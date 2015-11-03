package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 2014-10-27.
 */
public class RouteFinder {
    private List<City> cities;

    public RouteFinder(List<City> c) {
        cities = c;
    }

    public Route findRoute(City cityFrom, City cityTo) {

        if (cityFrom.equals(cityTo)) {
            return new Route(cityFrom);
        }
        int maxWeight = findMaxWeight() + 1;
//        System.out.println(maxWeight);
        List<City> s = new ArrayList<City>();
        List<City> q = copyList(cities);
        int[] cost = new int[cities.size()];
        int[] former = new int[cities.size()];


        //wypełnienie tablicy kosztów maksymalnymi wartościami
        //wypełnienie tablicy poprzedników wartosciamu -1
        for (int i = 0; i < cost.length; i++) {
            cost[i] = maxWeight;
            former[i] = -1;
        }
        cost[cityFrom.getId()] = 0;  //ustawienie elementu startowego na 0
        while (!q.isEmpty()) {

            //szukanie miasta o najmniejszym koszcie dojścia ze zbioru q
            int currentMax = maxWeight;
            int currentMaxIndex = 0;
            for (int i = 0; i < cost.length; i++) {
                if (q.contains(findCityById(i)) && cost[i] <= currentMax) {
                    currentMax = cost[i];
                    currentMaxIndex = i;
                }

            }

            //przenosimy znaleznione miasto ze zbioru q do s
            City city = findCityById(currentMaxIndex);
            s.add(city);
            q.remove(city);


            //przeglądamy wszystkich sąsiadów przeniesionego wierzchołka
            List<City> associates = city.getAssociations();
            int formerId = city.getId();
            for (City c : associates) {
                int id = c.getId();
                if ((q.contains(c)) && (cost[id] > cost[formerId] + city.getAssociationWeight(c))) {
                    cost[id] = cost[formerId] + city.getAssociationWeight(c);
                    former[id] = formerId;
                }
            }
        }

        //zapisywanie wyników(drogi) do Route
        Route route = new Route();
        route.setCost(cost[cityTo.getId()]);
        int theFormer = cityTo.getId(); //sztywna inicjalizacja
        City tmpCity = cityTo;
        route.addConection(cityTo);
        while (theFormer != cityFrom.getId()) {
            theFormer = former[tmpCity.getId()];
            tmpCity = findCityById(theFormer);
            route.addConection(tmpCity);
        }

        return route;
    }

    private City findCityById(int id) {
        City city = null;
        for (City c : cities) {
            if (c.getId() == id) {
                city = c;
                break;
            }
        }
        return city;
    }

    private int findMaxWeight() {
        int max = 0;
        for (City city : cities) {
            List<City> associations = city.getAssociations();
            for (City c : associations) {
                max += city.getAssociationWeight(c);
            }
        }
//        System.out.println(max);
        return max;
    }

    private List<City> copyList(List<City> c) {
        List<City> cities = new ArrayList<City>();
        for (int i = 0; i < c.size(); i++) {
            cities.add(c.get(i));
        }
        return cities;
    }
}
