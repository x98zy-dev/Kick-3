package org.x98zy.multithreadapp.state.impl;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnloadingState implements FerryState {
    private static final Logger logger = LogManager.getLogger(UnloadingState.class);
    private final Ferry ferry;

    public UnloadingState(Ferry ferry) {
        this.ferry = ferry;
        startUnloading();
    }

    private void startUnloading() {
        int unloadedCount = ferry.getLoadedCars().size();
        ferry.removeAllCars();
        logger.info("Unloaded {} cars", unloadedCount);
        ferry.setState(new WaitingState(ferry));
    }

    @Override
    public void depart() throws FerrySimulationException {
        throw new FerrySimulationException("Cannot depart while unloading");
    }

    @Override
    public void arrive() throws FerrySimulationException {
        throw new FerrySimulationException("Ferry has already arrived");
    }

    @Override
    public void loadCar(Car car) throws FerrySimulationException {
        throw new FerrySimulationException("Cannot load car while unloading");
    }
}