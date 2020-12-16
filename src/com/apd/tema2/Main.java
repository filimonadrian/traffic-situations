package com.apd.tema2;

import com.apd.tema2.entities.Intersection;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.io.Reader;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static Pedestrians pedestrians = null;
    public static Intersection intersection;
    public static int carsNo;
    public static CyclicBarrier barrierStrictXCars;
    public static CyclicBarrier barrier;
    public static CyclicBarrier barrierSimpleMaintenance;
    public static Semaphore semaphore;
    public static Semaphore semaphoreZero;
    public static Semaphore semaphoreOne;

    public static Semaphore singlePermitSemaphore;

    public static BlockingQueue<Integer> queue;

    public static List<Integer> differentIds;
    public static List<Semaphore> semaphores;
    public static AtomicInteger carsInIntersection = new AtomicInteger(0);
    public static AtomicInteger priorityCarsPassed = new AtomicInteger(0);
    public static AtomicInteger bool = new AtomicInteger(0);


    public static final Boolean crosswalkLock = true;

    public static void main(String[] args) {
        Reader fileReader = Reader.getInstance(args[0]);
        Set<Thread> cars = fileReader.getCarsFromInput();

        singlePermitSemaphore =  new Semaphore(1);

        if (intersection.getMaxCars() != null) {
            semaphore = new Semaphore(intersection.getMaxCars());
        }

        if (intersection.getLanesNo() != null) {
            differentIds = new ArrayList<Integer>(Collections.nCopies(intersection.getLanesNo(), 0));
            barrier = new CyclicBarrier(intersection.getMaxCars() * intersection.getLanesNo());
            semaphores = new ArrayList<Semaphore>();
            for (int i = 0; i < intersection.getLanesNo(); i++) {
                semaphores.add(new Semaphore(intersection.getMaxCars()));
            }
        }

        if (intersection.getName().equals("simple_strict_x_car_roundabout")) {
            barrierStrictXCars = new CyclicBarrier(intersection.getMaxCars() * intersection.getLanesNo());
        }

        if (intersection.getName().equals("priority_intersection")) {
            queue = new LinkedBlockingQueue<>(intersection.getLowPriorityCarsNo());
        }

        if (intersection.getName().equals("railroad")) {
            queue = new LinkedBlockingQueue<>(Main.carsNo);
            barrier = new CyclicBarrier(Main.carsNo);
        }

        if (intersection.getName().equals("simple_maintenance")) {
            // semaphore for both lanes
            semaphoreZero = new Semaphore(intersection.getCarsPassNo());
            semaphoreOne = new Semaphore(intersection.getCarsPassNo());
            barrierSimpleMaintenance = new CyclicBarrier(Main.intersection.getCarsPassNo());
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
