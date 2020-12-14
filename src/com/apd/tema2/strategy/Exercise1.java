package com.apd.tema2.strategy;

import com.apd.tema2.entities.Car;

public class Exercise1 implements ExerciseStrategy {
    @Override
    public void runExercise(Car car) {
        System.out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");
        car.sleep();
        System.out.println("Car " + car.getId() + " has waited enough, now driving...");

    }
}

class Exercise2 implements ExerciseStrategy {
    @Override
    public void runExercise(Car car) {

    }
}
