package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 2014-11-23.
 */
public class Car {
    private int capacity = 0;
    private List<Package> packages = new ArrayList<Package>();
    private Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(int x, int y) {
        Point p = new Point(x, y);
        this.point = p;
    }

    public void setPackagesAmount(int packagesAmount) {
        this.packagesAmount = packagesAmount;
    }

    private int packagesAmount = 0;
    int id;
    private Route route = null;
    private int distance = 0;

    public Car(int id, int capacity) {
        this.capacity = capacity;
        this.id = id;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public int getDistance() {
        return distance;
    }

    public void addDistance(int distance) {
        this.distance += distance;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void addPackage(Package p) {
        this.packages.add(p);
        this.packagesAmount++;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public boolean isOnRoute(Route r) {
        if (route != null) {
            if (route.contains(r)) return true;
            else return false;
        } else
            return true;
    }

    public boolean isFull() {
        if (packagesAmount >= capacity) return true;
        return false;
    }

    @Override
    public String toString() {
        return "ilość paczek: " + packagesAmount;
    }
}
