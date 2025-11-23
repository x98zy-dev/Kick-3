package org.x98zy.multithreadapp.entity;

import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.state.impl.WaitingState;

import java.util.ArrayList;
import java.util.List;

public class Ferry {

    private FerryState state;
    private final int maxCapacity;
    private final int maxArea;
    private final List<Car> loadedCars = new ArrayList<>();

    public Ferry(int maxCapacity, int maxArea) {
        this.maxArea = maxArea;
        this.maxCapacity = maxCapacity;
        this.state = new WaitingState(this);
    }

    public void setState(FerryState state) {
        this.state = state;
    }

    public boolean canLoad(Car car) {
        if (getCurrentWeight() + car.getWeight() > maxCapacity) {
            return false;
        }
        return (getCurrentArea() + car.getArea() < maxArea);
    }

    public void addCar(Car car) {
        loadedCars.add(car);
    }

    public void removeAllCars() {
        loadedCars.clear();
    }

    public int getCurrentWeight() {
        return loadedCars.stream().mapToInt(Car::getWeight).sum();
    }

    public int getCurrentArea() {
        return loadedCars.stream().mapToInt(Car::getArea).sum();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getMaxArea() {
        return maxArea;
    }
}
