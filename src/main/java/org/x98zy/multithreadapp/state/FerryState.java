package org.x98zy.multithreadapp.state;

import org.x98zy.multithreadapp.entity.Car;

public interface FerryState {

    void loadCar(Car car);
    void depart();
    void arrive();
}
