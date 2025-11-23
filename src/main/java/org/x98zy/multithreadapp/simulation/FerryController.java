package org.x98zy.multithreadapp.simulation;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.service.FerryService;
import org.x98zy.multithreadapp.util.ConfigLoader;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class FerryController implements Callable<Integer> {
    private static final Logger logger = LogManager.getLogger(FerryController.class);
    private final FerryService ferryService;
    private final ConfigLoader config;
    private volatile boolean shouldStop = false;

    public FerryController(FerryService ferryService, ConfigLoader config) {
        this.ferryService = ferryService;
        this.config = config;
    }

    @Override
    public Integer call() throws Exception {
        logger.debug("Starting ferry controller");
        int transportedCars = 0;
        int emptyCycles = 0;
        final int maxEmptyCycles = config.getInt("ferry.max.empty.cycles");

        while (!shouldStop) {
            boolean loaded = loadCarsFromQueue();

            if (!ferryService.getFerry().getLoadedCars().isEmpty()) {
                int carCount = ferryService.getFerry().getLoadedCars().size();
                logger.info("Ferry departing with {} cars", carCount);

                ferryService.getFerry().getState().depart();
                TimeUnit.MILLISECONDS.sleep(config.getInt("ferry.travel.time"));

                ferryService.getFerry().getState().arrive();
                logger.debug("Started unloading ferry");
                TimeUnit.SECONDS.sleep(1);

                transportedCars += carCount;
                ferryService.getFerry().removeAllCars();
                emptyCycles = 0;
            } else {
                emptyCycles++;
                TimeUnit.MILLISECONDS.sleep(500);
            }

            if (emptyCycles >= maxEmptyCycles && ferryService.isQueueEmpty()) {
                shouldStop = true;
            }
        }

        logger.info("Ferry completed work. Total transported: {} cars", transportedCars);
        return transportedCars;
    }

    private boolean loadCarsFromQueue() {
        boolean loadedAny = false;
        Car car;
        while ((car = ferryService.getCarFromQueue()) != null) {
            try {
                ferryService.getFerry().getState().loadCar(car);
                loadedAny = true;
                logger.debug("Car {} loaded onto ferry", car.getType());
            } catch (FerrySimulationException e) {
                logger.warn("Failed to load car: {}", e.getMessage());
                ferryService.addCarToQueue(car);
                break;
            }
        }
        return loadedAny;
    }

    public void stop() {
        shouldStop = true;
        logger.debug("Stop command received");
    }
}