package org.x98zy.multithreadapp.service;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;
import org.x98zy.multithreadapp.exception.FerrySimulationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicReference;

public class FerryService {
    private static final Logger logger = LogManager.getLogger(FerryService.class);
    private static final AtomicReference<FerryService> INSTANCE = new AtomicReference<>();

    private final Ferry ferry;
    private final Queue<Car> carQueue;
    private final ReentrantLock lock;

    private FerryService(int ferryCapacity, int ferryArea) {
        this.ferry = new Ferry(ferryCapacity, ferryArea);
        this.carQueue = new ArrayDeque<>();
        this.lock = new ReentrantLock();
    }

    public static FerryService getInstance(int ferryCapacity, int ferryArea) {
        while (true) {
            FerryService current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new FerryService(ferryCapacity, ferryArea);
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public void addCarToQueue(Car car) {
        lock.lock();
        try {
            carQueue.offer(car);
            logger.debug("Car {} added to queue. Queue size: {}", car.getType(), carQueue.size());
        } finally {
            lock.unlock();
        }
    }

    public Car getCarFromQueue() {
        lock.lock();
        try {
            return carQueue.poll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isQueueEmpty() {
        lock.lock();
        try {
            return carQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public int getQueueSize() {
        lock.lock();
        try {
            return carQueue.size();
        } finally {
            lock.unlock();
        }
    }

    public Ferry getFerry() {
        return ferry;
    }
}