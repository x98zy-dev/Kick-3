package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;

public class TravellingState implements FerryState {

    private final Ferry ferry;

    TravellingState(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void arrive() {
        ferry.setState(new UnloadingState(ferry));
    }

    @Override
    public void loadCar(Car car) {
        System.out.println("Car can't be loaded in travelling state");
    }

    @Override
    public void depart() {
        System.out.println("Can't depart in travelling state");
    }
}
