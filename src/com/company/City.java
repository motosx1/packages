package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 2014-10-27.
 */
public class City {
    private int id;
    private String name;
    private List<City> associations = new ArrayList<City>();
    private List<Integer> weights = new ArrayList<Integer>();

    private List<Package> packages = new ArrayList<Package>();

    private Point point;

    public List<Package> getPackages() {
        return packages;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<City> getAssociations() {
        return associations;
    }

    public int getAssociationWeight(City c) {
        return weights.get(associations.indexOf(c));
    }

    public void addAssociation(City c, int weight) {
        associations.add(c);
        weights.add(weight);
    }

    public void deleteAssociation(City c) {
        int index = associations.indexOf(c);
        associations.remove(index);
        weights.remove(index);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final City other = (City) obj;

        if (this.id != other.id) {
            return false;
        }
        return true;
    }


}
