package org.x98zy.multithreadapp.entity;

public class Car {

    private final String type;
    private final int weight;
    private final int area;

    public Car(String type, int weight,int area) {
        this.type = type;
        this.weight = weight;
        this.area = area;
    }

    public int getArea() {
        return area;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

}
