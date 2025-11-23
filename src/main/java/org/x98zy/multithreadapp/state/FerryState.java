package org.x98zy.multithreadapp.state;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.exception.FerrySimulationException;

public interface FerryState {
    void loadCar(Car car) throws FerrySimulationException;
    void depart() throws FerrySimulationException;
    void arrive() throws FerrySimulationException;
}