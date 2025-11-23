package org.x98zy.multithreadapp.service;

import org.x98zy.multithreadapp.entity.Car;
import org.x98zy.multithreadapp.entity.Ferry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class FerryService {

    private final FerryService instance;
    private final Ferry ferry;
    private final Queue<Car> carQueue;
    private final ReentrantLock lock;

    private FerryService(int ferryCapacity, int ferryArea) {
        this.ferry = new Ferry(ferryCapacity, ferryArea);
        this.carQueue = new LinkedList<>();
        this.lock = new ReentrantLock();
    }

    private instance getInstance(int ferryCapacity, int ferryArea) {
        if (instance == null) {
            instance = new FerryService(ferryCapacity, ferryArea);
        }
        return instance;
    }

    public void addCarToQueue(Car car) {
        lock.lock();
        try() {
            carQueue.offer(car);
        } finally {
            lock.unlock();
        }
    }
}
