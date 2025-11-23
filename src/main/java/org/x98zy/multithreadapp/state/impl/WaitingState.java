package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;

public class WaitingState implements FerryState {

    private final Ferry ferry;

    WaitingState(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void loadCar(Car car) {
        if(ferry.canLoad(car)) {
            ferry.addCar(car);
        }
        else {

        }
    }

    @Override
    public void depart() {
        ferry.setState(new TravellingState(ferry));
    }

    @Override
    public void arrive() {
        System.out.println("The ferry had arrived already");
    }

}
