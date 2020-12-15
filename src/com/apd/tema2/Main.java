package com.apd.tema2;

import com.apd.tema2.entities.Car;
import com.apd.tema2.entities.Intersection;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.io.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static Pedestrians pedestrians = null;
    public static Intersection intersection;
    public static int carsNo;
    public static CyclicBarrier barrier;
    public static Semaphore semaphore;

    public static List<Integer> differentIds;
    public static List<Semaphore> semaphores;
    public static AtomicInteger carsInIntersection = new AtomicInteger(0);

    public static final Boolean crosswalkLock = true;

    public static void main(String[] args) {
        Reader fileReader = Reader.getInstance(args[0]);
        Set<Thread> cars = fileReader.getCarsFromInput();

        if (intersection.getMaxCars() != null) {
            // Car.differentIds = new ArrayList<Integer>(Collections.nCopies(intersection.getMaxCars(), 0));
            semaphore = new Semaphore(intersection.getMaxCars() * intersection.getLanesNo());
        }
        if (intersection.getLanesNo() != null) {
            differentIds = new ArrayList<Integer>(Collections.nCopies(intersection.getLanesNo(), 0));
            barrier = new CyclicBarrier(intersection.getMaxCars() * intersection.getLanesNo());
            semaphores = new ArrayList<Semaphore>();
            for (int i = 0; i < intersection.getLanesNo(); i++) {
                semaphores.add(new Semaphore(intersection.getMaxCars()));
            }
        }

        for(Thread car : cars) {
            car.start();
        }

        if(pedestrians != null) {
            try {
                Thread p = new Thread(pedestrians);
                p.start();
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(Thread car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
