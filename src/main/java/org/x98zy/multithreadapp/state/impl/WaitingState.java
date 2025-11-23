package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WaitingState implements FerryState {
    private static final Logger logger = LogManager.getLogger(WaitingState.class);
    private final Ferry ferry;

    public WaitingState(Ferry ferry) {
        this.ferry = ferry;
    }

    @Override
    public void loadCar(Car car) throws FerrySimulationException {
        if (ferry.canLoad(car)) {
            ferry.addCar(car);
            logger.debug("Car loaded onto ferry");
        } else {
            throw new FerrySimulationException("Cannot load car - ferry is full");
        }
    }

    @Override
    public void depart() {
        logger.info("Ferry departing");
        ferry.setState(new TravellingState(ferry));
    }

    @Override
    public void arrive() {
        logger.warn("Ferry is already at the dock");
    }
}