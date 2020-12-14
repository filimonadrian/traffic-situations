package com.apd.tema2.entities;

import com.apd.tema2.Main;
import com.apd.tema2.strategy.Exercise1;
import com.apd.tema2.strategy.ExerciseStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static List<Integer> differentIds;// = Main.differentIds;

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

        ExerciseStrategy strategy;
        switch (Main.intersection.getName()) {
            case("simple_semaphore"):
                strategy = new Exercise1();
                strategy.runExercise(this);
                break;
            case("simple_n_roundabout"):

                System.out.println("Car " + this.id + " has reached the roundabout, now waiting...");

                try {
                    Main.semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Car " + id + " has entered the roundabout");

                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException ex) {
                    System.out.println(ex.getStackTrace());
                }

                System.out.println("Car " + id +
                        " has exited the roundabout after " +
                        Main.intersection.getTime()/1000  + " seconds");
                Main.semaphore.release();

                break;

            case("simple_strict_1_car_roundabout"):
                System.out.println("Car " + this.id + " has reached the roundabout");
                synchronized (Main.differentIds) {
                    while(true) {
                        if (Main.differentIds.get(this.startDirection) == 1) {
                            try {
                                Main.differentIds.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // if it's the first car in this direction, set flag and GOO
                            Main.differentIds.set(this.startDirection, 1);
                            break;
                        }

                    }
                }

                System.out.println("Car " + this.id + " has entered the roundabout from lane " + this.startDirection);

                this.sleep();
                System.out.println("Car " + id +
                        " has exited the roundabout after " +
                        Main.intersection.getTime()/1000  + " seconds");
                synchronized (Main.differentIds) {
                    Main.differentIds.set(this.startDirection, 0);
                    Main.differentIds.notifyAll();
                }

                break;

            case("simple_strict_x_car_roundabout"):
                System.out.println("Car " + this.id + " has reached the roundabout, now waiting...");
                synchronized (Main.differentIds) {
                    while(true) {
                        if (Main.differentIds.get(this.startDirection).equals(Main.intersection.getMaxCars())) {
                            try {
                                Main.differentIds.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Car " + this.id + " was selected to enter the roundabout from lane " + this.startDirection);
                            Main.differentIds.set(this.startDirection, Main.differentIds.get(this.startDirection) + 1);
                            break;
                        }
                    }
                }

                try {
                    Main.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                System.out.println("Car " + this.id + " has entered the roundabout from lane " + this.startDirection);

                try {
                    Main.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                this.sleep();

                System.out.println("Car " + id +
                        " has exited the roundabout after " +
                        Main.intersection.getTime()/1000  + " seconds");
                try {
                    Main.barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                synchronized (Main.differentIds) {
                    Main.differentIds.set(this.startDirection, 0);
                    Main.differentIds.notifyAll();
                }

                break;

            case("simple_max_x_car_roundabout"):  // not sure what to do! - also check the ReaderHandler!
                System.out.println("Car " + this.id + " has reached the roundabout from lane " + this.getStartDirection());
                synchronized (Main.differentIds) {
                    while(true) {
                        if (Main.differentIds.get(this.startDirection) == Main.intersection.getMaxCars()) {
                            try {
                                Main.differentIds.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // if it's the first car in this direction, set flag and GOO
                            Main.differentIds.set(this.startDirection, 1);
                            break;
                        }
                    }
                }

                System.out.println("Car " + this.id + " has entered the roundabout from lane " + this.startDirection);

                this.sleep();
                System.out.println("Car " + id +
                        " has exited the roundabout after " +
                        Main.intersection.getTime()/1000  + " seconds");
                synchronized (Main.differentIds) {
                    Main.differentIds.set(this.startDirection, Main.differentIds.get(this.startDirection) - 1);
                    Main.differentIds.notifyAll();
                }
                break;

            case("priority_intersection"):
                // if the car has high priority, increment contor(the car is in intersection) and traverse
                if (this.priority > 1) {
                    Main.carsInIntersection.incrementAndGet();
                    System.out.println("Car " + this.id + " with high priority has entered the intersection");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Car " + this.id + " with high priority has exited the intersection");
                    Main.carsInIntersection.decrementAndGet();
                }

                // if the car has low priority, try to enter the intersection
                // if there is no priority car, traverse the intersection
                 else if (this.priority == 1) {
                    System.out.println("Car " + this.id + " with low priority is trying to enter the intersection...");
                    while (true) {
                        if (Main.carsInIntersection.get() == 0) {
                            System.out.println("Car " + this.id + " with low priority has entered the intersection");
                            //System.out.println("Car " + this.id + " with low priority has exited the intersection");
                            break;
                        }
                    }
                }

                break;

            case("crosswalk"):

                break;

            case("simple_maintenance"):

                break;

            case("complex_maintenance"):

                break;

            case("railroad"):

                break;

        }

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

    public void sleep() {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
