package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.intersections.*;
import com.apd.tema2.utils.Constants;

import java.util.concurrent.BrokenBarrierException;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");
                    car.sleep();
                    System.out.println("Car " + car.getId() + " has waited enough, now driving...");
                }
            };
            case "simple_n_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

                    try {
                        Main.semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has entered the roundabout");

                    try {
                        Thread.sleep(car.getWaitingTime());
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getStackTrace());
                    }

                    System.out.println("Car " + car.getId() +
                            " has exited the roundabout after " +
                            Main.intersection.getTime()/1000  + " seconds");
                    Main.semaphore.release();
                }
            };
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout");
                    synchronized (Main.differentIds) {
                        while(true) {
                            if (Main.differentIds.get(car.getStartDirection()) == 1) {
                                try {
                                    Main.differentIds.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // if it's the first car in this direction, set flag and GOO
                                Main.differentIds.set(car.getStartDirection(), 1);
                                break;
                            }
                        }
                    }

                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + car.getStartDirection());

                    car.sleep();
                    System.out.println("Car " + car.getId() +
                            " has exited the roundabout after " +
                            Main.intersection.getTime()/1000  + " seconds");
                    synchronized (Main.differentIds) {
                        Main.differentIds.set(car.getStartDirection(), 0);
                        Main.differentIds.notifyAll();
                    }
                }
            };
            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

                    try {
                        Main.semaphores.get(car.getStartDirection()).acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane " + car.getStartDirection());

                    try {
                        Main.barrierStrictXCars.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + car.getStartDirection());
                    car.sleep();

                    try {
                        Main.barrierStrictXCars.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() +
                            " has exited the roundabout after " +
                            Main.intersection.getTime()/1000  + " seconds");
                    try {
                        Main.barrierStrictXCars.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    Main.semaphores.get(car.getStartDirection()).release();
                }
            };
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici

                    System.out.println("Car " + car.getId() + " has reached the roundabout from lane " + car.getStartDirection());

                    try {
                        Main.semaphores.get(car.getStartDirection()).acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has entered the roundabout from lane " + car.getStartDirection());


                    try {
                        Thread.sleep(Main.intersection.getTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() +
                            " has exited the roundabout after " +
                            Main.intersection.getTime()/1000  + " seconds");

                    Main.semaphores.get(car.getStartDirection()).release();
                }
            };
            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // if the car has high priority, increment contor(the car is in intersection) and traverse
                    if (car.getPriority() > 1) {
                        Main.carsInIntersection.incrementAndGet();
                        System.out.println("Car " + car.getId()+ " with high priority has entered the intersection");

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Car " + car.getId() + " with high priority has exited the intersection");
                        Main.carsInIntersection.decrementAndGet();
                    }

                    // if the car has low priority, try to enter the intersection
                    else if (car.getPriority() == 1) {
                        try {
                            Main.singlePermitSemaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Car " + car.getId() + " with low priority is trying to enter the intersection...");
                        Main.queue.add(car.getId());

                        Main.singlePermitSemaphore.release();
                        while (true) {
                            // if there is no priority car, traverse the intersection, KEEP THE ORDER
                            if (Main.carsInIntersection.get() == 0) {
                                while (true) {
                                    if (Main.queue.element() == car.getId()) {
                                        Main.queue.poll();
                                        System.out.println("Car " + car.getId() + " with low priority has entered the intersection");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            };
            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                    // lastColor: 0 pentru green, 1 pentru red
                    Main.lastColor.set(0);

                    System.out.println("Car " + car.getId() + " has now green light");

                    // the cars are going in the circle
                    while (!Main.pedestrians.isFinished()) {

                        // if the pedestrians have green color and the last printed color was green, print red
                        if (Main.pedestrians.isPass() && Main.lastColor.get() == 0) {
                            System.out.println("Car " + car.getId() + " has now red light");
                            Main.lastColor.set(1);
                            // if the cars have green and the last printed color was red, print green
                        } else if (!Main.pedestrians.isPass() && Main.lastColor.get() == 1) {
                            System.out.println("Car " + car.getId() + " has now green light");
                            Main.lastColor.set(0);
                        }
                    }
                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car){

                    // the car arrives at the bottleneck
                    System.out.println("Car " + car.getId() +
                            " from side number " + car.getStartDirection() +
                            " has reached the bottleneck");

                    // wait to arrive all the cars to the bottleneck
                    try {
                        Main.barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    // if it's on the lane 0
                    if (car.getStartDirection() == 0) {
                        // acquire the semaphore
                        try {
                            Main.semaphoreZero.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        while (true) {
                            if (Main.bool.get() == 0) {
                                // pass the intersection
                                System.out.println("Car " + car.getId() +
                                        " from side number " + car.getStartDirection() +
                                        " has passed the bottleneck");
                                break;
                            }
                        }
                        // wait all cars --- be sure that all cars from lane 0 passed
                        try {
                            Main.barrierSimpleMaintenance.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        synchronized (Main.bool) {
                            Main.bool.notifyAll();
                            Main.bool.set(1);
                        }
                    }

                    // if it's on the lane 1
                    if (car.getStartDirection() == 1) {
                        // acquire the semaphore
                        try {
                            Main.semaphoreOne.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // IF ALL CARS FROM LANE 0 PASSED THE INTERSECTION, I CAN PASS THE INTERSECTION
                        synchronized (Main.bool) {
                            while (true) {
                                if (Main.bool.get() == 0) {
                                    try {
                                        Main.bool.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                // pass the intersection
                                System.out.println("Car " + car.getId() +
                                        " from side number " + car.getStartDirection() +
                                        " has passed the bottleneck");
                                break;
                            }

                        }

                        // wait all cars --- be sure that all cars from lane 1 passed
                        try {
                            Main.barrierSimpleMaintenance.await();
                        } catch (InterruptedException | BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                        Main.bool.set(0);
                    }


                    // after cars from both lanes passed, we can repeat all
                    if (car.getStartDirection() == 0) {
                        Main.semaphoreZero.release();
                    } else {
                        Main.semaphoreOne.release();
                    }

                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                    try {
                        Main.singlePermitSemaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Main.queue.add(car.getId());


                    System.out.println("Car " + car.getId() +
                                        " from side number " + car.getStartDirection() +
                                        " has stopped by the railroad");

                    Main.singlePermitSemaphore.release();

                    try {
                        Main.barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    if (car.getId() == 0) {
                        System.out.println("The train has passed, cars can now proceed");
                    }

                    while (true) {
                        if (Main.queue.element() == car.getId()) {
                            System.out.println("Car " + car.getId() +
                                    " from side number " + car.getStartDirection() +
                                    " has started driving");
                                Main.queue.poll();
                            break;
                        }
                    }
                }
            };
            default -> null;
        };
    }
}
