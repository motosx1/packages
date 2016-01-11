package com.company;

import exceptions.WrongDataFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 2014-10-26.
 */
public class Reader {

    public List<City> loadCities(String path) throws WrongDataFormatException{
        BufferedReader br = null;
        List<City> cities = new ArrayList<City>();

        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            int savedId = -1;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.split(" ")[0].compareTo("#") != 0) {
                    int id = Integer.parseInt(sCurrentLine.split(" ")[0]);
                    String name = sCurrentLine.split(" ")[1];
                    for (int i = 2; i < sCurrentLine.split(" ").length; i++)
                        name += " " + sCurrentLine.split(" ")[i];
                    if (isAlreadyExisting(new City(id, name), cities)) {
                        throw new WrongDataFormatException("The city "+ name +" with id "+ id +" already exists.");
                    } else {
                        if ((savedId++) + 1 == id) {
                            cities.add(new City(id, name));
                        } else {
                            throw new WrongDataFormatException("Wrong Data Format in file with cities.");
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return cities;
        }


    }

    public int getStartCityId(String path) {
        int startCityId = 0;
        BufferedReader br = null;
        List<Package> packageList = new ArrayList<Package>();
        try {

            String line;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (line.split(" ")[0].compareTo("#") != 0) {
                    if (line.split(" ").length == 1) {
                        startCityId = Integer.parseInt(line);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return startCityId;
    }

    public void loadAssociations(String path, List<City> cities) {
        BufferedReader br = null;

        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.split(" ")[0].compareTo("#") != 0) {
                    if (exists(Integer.parseInt(sCurrentLine.split(" ")[1]), cities) && exists(Integer.parseInt(sCurrentLine.split(" ")[0]), cities)) { //sprawdzamy czy miasto z danym id istnieje.
                        City c = findCityById(Integer.parseInt(sCurrentLine.split(" ")[0]), cities);
                        c.addAssociation(findCityById(Integer.parseInt(sCurrentLine.split(" ")[1]), cities), Integer.parseInt(sCurrentLine.split(" ")[2]));
                        //lustrzane połącznie
                        c = findCityById(Integer.parseInt(sCurrentLine.split(" ")[1]), cities);
                        c.addAssociation(findCityById(Integer.parseInt(sCurrentLine.split(" ")[0]), cities), Integer.parseInt(sCurrentLine.split(" ")[2]));

                    } else {
                        //throw odrzucono pozycję
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }


    }


    private boolean exists(int id, List<City> cities) {
        for (City c : cities) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyExisting(City city, List<City> cities) {
        for (City c : cities) {
            if (city.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private City findCityById(int id, List<City> cities) {
        City city = null;
        for (City c : cities) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public String readLine(String path) {
        BufferedReader br = null;

        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            if ((sCurrentLine = br.readLine()) != null) {
                return sCurrentLine;
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return null;
    }

    public int countLines(String path) {
        BufferedReader br = null;
        int count = 0;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.split(" ")[0].compareTo("#") != 0) {
                    count++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return count;
        }

    }

    public PackageStorage loadPackages(String path, List<City> cities) {
        PackageStorage packageStorage = new PackageStorage();
        BufferedReader br = null;

        try {

            String line;
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (line.split(" ")[0].compareTo("#") != 0) {
                    if (line.split(" ").length != 1) {
                        Package p = new Package();
                        p.setId(Integer.parseInt(line.split(" ")[0]));

                        p.setStartCity(findCityById(Integer.parseInt(line.split(" ")[1]), cities));

                        p.setDestinationCity(findCityById(Integer.parseInt(line.split(" ")[2]), cities));

                        int descriptionStart = 3;
                        int descriptionStop = line.split(" ").length - 2;
                        String description = "";
                        for (int i = descriptionStart; i <= descriptionStop; i++)
                            description += line.split(" ")[i] + " ";
                        p.setDescription(description);

                        p.setPriority(Integer.parseInt(line.split(" ")[line.split(" ").length - 1]));

                        packageStorage.push(p);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        return packageStorage;
    }

    public String readFromConsole(String text) {
        System.out.println(text + " ");

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();

            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
