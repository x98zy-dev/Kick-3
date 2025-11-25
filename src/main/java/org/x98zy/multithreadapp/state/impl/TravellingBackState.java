package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TravellingBackState implements FerryState {
    private static final Logger logger = LogManager.getLogger(TravellingBackState.class);
    private final Ferry ferry;

    public TravellingBackState(Ferry ferry) {
        this.ferry = ferry;
        logger.info("Ferry started journey back to port A");
    }

    @Override
    public void arrive() {
        logger.info("Ferry returned to port A");
        ferry.setState(new WaitingState(ferry));
    }

    @Override
    public void loadCar(Car car) throws FerrySimulationException {
        throw new FerrySimulationException("Cannot load car while travelling back");
    }

    @Override
    public void depart() throws FerrySimulationException {
        throw new FerrySimulationException("Ferry is already travelling back");
    }

    @Override
    public void startUnloading() throws FerrySimulationException {
        throw new FerrySimulationException("Cannot unload while travelling back");
    }
}