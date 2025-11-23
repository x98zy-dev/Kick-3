package org.x98zy.multithreadapp.main;

import org.x98zy.multithreadapp.service.FerryService;
import org.x98zy.multithreadapp.simulation.CarGenerator;
import org.x98zy.multithreadapp.simulation.FerryController;
import org.x98zy.multithreadapp.util.ConfigLoader;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            ConfigLoader config = new ConfigLoader();
            int ferryCapacity = config.getInt("ferry.max.capacity");
            int ferryArea = config.getInt("ferry.max.area");
            int carCount = config.getInt("car.generation.count");
            int timeout = config.getInt("simulation.timeout.seconds");

            logger.info("Starting ferry simulation: capacity={}, area={}, cars={}",
                    ferryCapacity, ferryArea, carCount);

            FerryService ferryService = FerryService.getInstance(ferryCapacity, ferryArea);
            ExecutorService executor = Executors.newFixedThreadPool(2);

            CarGenerator carGenerator = new CarGenerator(ferryService, carCount, config);
            FerryController ferryController = new FerryController(ferryService, config);

            Future<Integer> carsGenerated = executor.submit(carGenerator);
            Future<Integer> carsTransported = executor.submit(ferryController);

            try {
                int generatedCount = carsGenerated.get(timeout, TimeUnit.SECONDS);
                logger.info("Cars generated: {}", generatedCount);

                int transportedCount = carsTransported.get(timeout, TimeUnit.SECONDS);
                logger.info("Cars transported: {}", transportedCount);

            } catch (TimeoutException e) {
                logger.warn("Simulation timeout reached");
                ferryController.stop();
            } finally {
                executor.shutdown();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            }

            logger.info("Simulation completed successfully");

        } catch (FerrySimulationException e) {
            logger.error("Simulation error: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
        }
    }
}