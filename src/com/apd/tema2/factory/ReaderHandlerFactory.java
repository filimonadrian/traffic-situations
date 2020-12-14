package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.entities.ReaderHandler;
import com.apd.tema2.intersections.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) {
                    // Exemplu de utilizare:
                    // Main.intersection = IntersectionFactory.getIntersection("simpleIntersection");
                    Main.intersection = IntersectionFactory.getIntersection("simple_semaphore");
                    Main.intersection.setName("simple_semaphore");
                }
            };
            case "simple_n_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    // To parse input line use:
                    // String[] line = br.readLine().split(" ");
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("simple_n_roundabout");
                    Main.intersection.setName("simple_n_roundabout");

                    Main.intersection.setMaxCars(Integer.parseInt(line[0]));
                    Main.intersection.setTime(Integer.parseInt(line[1]));
                }
            };
            case "simple_strict_1_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("simple_strict_1_car_roundabout");
                    Main.intersection.setName("simple_strict_1_car_roundabout");

                    Main.intersection.setMaxCars(Integer.parseInt(line[0]));
                    Main.intersection.setTime(Integer.parseInt(line[1]));
                }
            };
            case "simple_strict_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("simple_strict_x_car_roundabout");
                    Main.intersection.setName("simple_strict_x_car_roundabout");

                    Main.intersection.setMaxCars(Integer.parseInt(line[0]));
                    Main.intersection.setTime(Integer.parseInt(line[1]));
                    Main.intersection.setMaxCarsLane(Integer.parseInt(line[2]));
                }
            };
            case "simple_max_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("simple_max_x_car_roundabout");
                    Main.intersection.setName("simple_max_x_car_roundabout");

                    Main.intersection.setMaxCars(Integer.parseInt(line[0]));
                    Main.intersection.setTime(Integer.parseInt(line[1]));
                    Main.intersection.setMaxCarsLane(Integer.parseInt(line[2]));

                }
            };
            case "priority_intersection" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("priority_intersection");
                    Main.intersection.setName("priority_intersection");

                    Main.intersection.setHighPriorityCarsNo(Integer.parseInt(line[0]));
                    Main.intersection.setLowPriorityCarsNo(Integer.parseInt(line[1]));
                }
            };
            case "crosswalk" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("crosswalk");
                    Main.intersection.setName("crosswalk");
                    Main.intersection.setPedestrianTime(Integer.parseInt(line[0]));
                    Main.intersection.setMaxPedestriansNo(Integer.parseInt(line[1]));
                }
            };
            case "simple_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");

                    Main.intersection = IntersectionFactory.getIntersection("simple_maintenance");
                    Main.intersection.setName("simple_maintenance");
                    Main.intersection.setCarsPassNo(Integer.parseInt(line[0]));
                }
            };
            case "complex_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("complex_maintenance");
                    Main.intersection.setName("complex_maintenance");


                }
            };
            case "railroad" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("railroad");
                    Main.intersection.setName("railroad");

                }
            };
            default -> null;
        };
    }

}
