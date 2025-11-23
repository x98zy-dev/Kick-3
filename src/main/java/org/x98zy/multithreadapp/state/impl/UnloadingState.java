package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;

public class UnloadingState implements FerryState {

    private final Ferry ferry;

    public UnloadingState(Ferry ferry) {
        this.ferry = ferry;
        ferry.removeAllCars();
        ferry.setState(new WaitingState(ferry));
    }

    @Override
    public void depart() {
        System.out.println("The ferry can't depart while unloading");
    }

    @Override
    public void arrive() {
        System.out.println("Ferry has already arrived");
    }

    @Override
    public void loadCar(Car car) {
        System.out.println("Car can't be loaded now");
    }
}
