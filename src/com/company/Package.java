package com.company;

/**
 * Created by Bartosz on 2014-11-06.
 */
public class Package {
    private int id;
    private City startCity;
    private City destinationCity;
    private String description;
    private int priority;
    public boolean isInCar = false;
    private int carId = -1;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public City getStartCity() {
        return startCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public void setStartCity(int cityId) {
        this.startCity = startCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Paczka: " + "id=" + getId() + " priorytet=" + getPriority() + " isInCar? = " + isInCar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package aPackage = (Package) o;

        if (id != aPackage.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
