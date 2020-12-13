package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

public class SimpleRoundabout implements Intersection {
    String name = "simple_n_roundabout";
    Integer maxCars, time;

    @Override
    public void setMaxCars(Integer maxCars) {
        this.maxCars = maxCars;
    }

    @Override
    public void setTime(Integer time) {
        this.time = time;
    }
}
