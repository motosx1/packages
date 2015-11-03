package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 2014-10-28.
 */
public class Route {
    private List<City> cities = new ArrayList<City>();
    private int cost = 0;

    public Route() {
        super();
    }

    public Route(City city) {
        cities.add(city);
    }

    public List<City> getCitiesList() {
        return cities;
    }

    public void addConection(City cityTo) {
        cities.add(cityTo);
//        System.out.println("dodano " + cityTo.getName());
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public void printWithWeights() {
        print();
        System.out.println("koszt: " + cost);
    }

    public void print() {
        System.out.println("droga: ");
        for (int i = cities.size() - 1; i > 0; i--)
            System.out.print(cities.get(i).getName() + " --> ");
        System.out.print(cities.get(0).getName());
        System.out.println();
    }


    public boolean contains(Route r) {
        List<City> rCities = r.getCitiesList();
        if (cities.size() < rCities.size()) return false;

        int last = cities.size() - 1;

        for (int i = rCities.size() - 1; i >= 0; i--, last--) {
            if (cities.get(last) != rCities.get(i))
                return false;


        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = cities.size() - 1; i > 0; i--)
            sb.append(cities.get(i).getId() + " --> ");
        sb.append(cities.get(0).getId());

        return sb.toString();
    }
}
