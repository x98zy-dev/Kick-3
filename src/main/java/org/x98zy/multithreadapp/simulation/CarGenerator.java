package org.x98zy.multithreadapp.simulation;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.service.FerryService;
import org.x98zy.multithreadapp.util.ConfigLoader;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CarGenerator implements Callable<Integer> {
    private static final Logger logger = LogManager.getLogger(CarGenerator.class);
    private final FerryService ferryService;
    private final int carCount;
    private final ConfigLoader config;

    public CarGenerator(FerryService ferryService, int carCount, ConfigLoader config) {
        this.ferryService = ferryService;
        this.carCount = carCount;
        this.config = config;
    }

    @Override
    public Integer call() throws Exception {
        logger.debug("Starting car generator");
        int generatedCars = 0;

        for (int i = 0; i < carCount; i++) {
            Car car = generateRandomCar();
            ferryService.addCarToQueue(car);
            generatedCars++;

            TimeUnit.MILLISECONDS.sleep(config.getInt("car.generation.delay"));
        }

        logger.info("Car generation completed: {} cars", generatedCars);
        return generatedCars;
    }

    private Car generateRandomCar() throws FerrySimulationException {
        String type = Math.random() > config.getDouble("car.type.ratio") ? "light" : "heavy";

        int weight = type.equals("light") ?
                config.getInt("car.light.weight.min") + (int) (Math.random() *
                        (config.getInt("car.light.weight.max") - config.getInt("car.light.weight.min"))) :
                config.getInt("car.heavy.weight.min") + (int) (Math.random() *
                        (config.getInt("car.heavy.weight.max") - config.getInt("car.heavy.weight.min")));

        int area = type.equals("light") ?
                config.getInt("car.light.area.min") + (int) (Math.random() *
                        (config.getInt("car.light.area.max") - config.getInt("car.light.area.min"))) :
                config.getInt("car.heavy.area.min") + (int) (Math.random() *
                        (config.getInt("car.heavy.area.max") - config.getInt("car.heavy.area.min")));

        logger.debug("Generated car: type={}, weight={}, area={}", type, weight, area);
        return new Car(type, weight, area);
    }
}