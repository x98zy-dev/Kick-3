package org.x98zy.multithreadapp.entity;

import org.x98zy.multithreadapp.state.FerryState;
import org.x98zy.multithreadapp.state.impl.WaitingState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class Ferry {
    private static final Logger logger = LogManager.getLogger(Ferry.class);

    private FerryState state;
    private final int maxCapacity;
    private final int maxArea;
    private final List<Car> loadedCars = new ArrayList<>();

    public Ferry(int maxCapacity, int maxArea) {
        if (maxCapacity <= 0 || maxArea <= 0) {
            throw new IllegalArgumentException("Ferry capacity and area must be positive");
        }
        this.maxArea = maxArea;
        this.maxCapacity = maxCapacity;
        this.state = new WaitingState(this);
        logger.debug("Created ferry: capacity={}, area={}", maxCapacity, maxArea);
    }

    public void setState(FerryState state) {
        logger.debug("State change: {} -> {}", this.state.getClass().getSimpleName(),
                state.getClass().getSimpleName());
        this.state = state;
    }

    public boolean canLoad(Car car) {
        int newWeight = getCurrentWeight() + car.getWeight();
        int newArea = getCurrentArea() + car.getArea();
        boolean canLoad = newWeight <= maxCapacity && newArea <= maxArea;
        logger.debug("Can load car {}: {}", car.getType(), canLoad);
        return canLoad;
    }

    public void addCar(Car car) {
        loadedCars.add(car);
        logger.debug("Car {} added to ferry. Loaded: {}", car.getType(), loadedCars.size());
    }

    public void removeAllCars() {
        int count = loadedCars.size();
        loadedCars.clear();
        logger.debug("All {} cars unloaded from ferry", count);
    }

    public int getCurrentWeight() {
        return loadedCars.stream().mapToInt(Car::getWeight).sum();
    }

    public int getCurrentArea() {
        return loadedCars.stream().mapToInt(Car::getArea).sum();
    }

    public int getMaxCapacity() { return maxCapacity; }
    public int getMaxArea() { return maxArea; }
    public List<Car> getLoadedCars() { return new ArrayList<>(loadedCars); }
    public FerryState getState() { return state; }
}