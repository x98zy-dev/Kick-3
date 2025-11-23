package org.x98zy.multithreadapp.entity;

import org.x98zy.multithreadapp.exception.FerrySimulationException;

public class Car {
    private final String type;
    private final int weight;
    private final int area;

    public Car(String type, int weight, int area) throws FerrySimulationException {
        validateParameters(type, weight, area);
        this.type = type;
        this.weight = weight;
        this.area = area;
    }

    private void validateParameters(String type, int weight, int area) throws FerrySimulationException {
        if (type == null || type.trim().isEmpty()) {
            throw new FerrySimulationException("Car type cannot be null or empty");
        }
        if (weight <= 0) {
            throw new FerrySimulationException("Car weight must be positive: " + weight);
        }
        if (area <= 0) {
            throw new FerrySimulationException("Car area must be positive: " + area);
        }
    }

    public String getType() { return type; }
    public int getWeight() { return weight; }
    public int getArea() { return area; }
}