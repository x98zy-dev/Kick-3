package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TravellingState implements FerryState {
    private static final Logger logger = LogManager.getLogger(TravellingState.class);
    private final Ferry ferry;

    public TravellingState(Ferry ferry) {
        this.ferry = ferry;
        logger.info("Ferry departed");
    }

    @Override
    public void arrive() {
        logger.info("Ferry arrived at destination");
        ferry.setState(new UnloadingState(ferry));
    }

    @Override
    public void loadCar(Car car) throws FerrySimulationException {
        throw new FerrySimulationException("Cannot load car while travelling");
    }

    @Override
    public void depart() throws FerrySimulationException {
        throw new FerrySimulationException("Ferry is already travelling");
    }
}