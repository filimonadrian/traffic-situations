package com.apd.tema2.entities;

import com.apd.tema2.Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Thread.sleep;

/**
 * Clasa Thread principala.
 */
public class Car implements Runnable {
    private final int id;
    private final int startDirection;
    private final int endDirection;
    private final int priority;
    private final int waitingTime;
    private final IntersectionHandler intersectionHandler;

    public Car(final int id, final int startDirection, final IntersectionHandler intersectionHandler) {
        this(id, startDirection, -1, 0, intersectionHandler, 1);
    }

    public Car(final int id, final int startDirection, final int waitingTime, final IntersectionHandler intersectionHandler) {
        this(id, startDirection, -1, waitingTime, intersectionHandler, 1);
        // System.out.println(id + " " + waitingTime + " ");
    }

    public Car(final int id, final int startDirection, final int endDirection, final int waitingTime, final IntersectionHandler intersectionHandler) {
        this(id, startDirection, endDirection, waitingTime, intersectionHandler, 1);
    }

    public Car(final int id, final int startDirection, final int endDirection, final int waitingTime, IntersectionHandler intersectionHandler, final int priority) {
        this.id = id;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
        this.waitingTime = waitingTime;
        this.intersectionHandler = intersectionHandler;
        this.priority = priority;
    }

    @Override
    public void run() {
        intersectionHandler.handle(this);
        // Exercitiul 1
//        System.out.println("Car " + this.id + " has reached the semaphore, now waiting...");
//        try {
//            sleep(waitingTime);
//        } catch (InterruptedException ex) {
//            System.out.println(ex.getStackTrace());
//        }
//        System.out.println("Car " + this.id + " has waited enough, now driving...");

        // Exercitiul 2
//        System.out.println("Car " + this.id + " has reached the roundabout, now waiting...");
//
//        try {
//            Main.semaphore.acquire();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Car " + id + " has entered the roundabout");
//
//        try {
//            sleep(waitingTime);
//        } catch (InterruptedException ex) {
//            System.out.println(ex.getStackTrace());
//        }
//
//        System.out.println("Car " + id +
//                " has exited the roundabout after " +
//                Main.intersection.getTime()/1000  + " seconds");
//        Main.semaphore.release();

        // Exercitiul 3

        System.out.println("Car " + this.id + " has reached the roundabout");


        System.out.println("Car " + this.id + " has entered the roundabout from lane ");


        System.out.println("Car " + id +
                " has exited the roundabout after " +
                Main.intersection.getTime()/1000  + " seconds");


    }

    public int getId() {
        return id;
    }

    public int getStartDirection() {
        return startDirection;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getPriority() {
        return priority;
    }
}
