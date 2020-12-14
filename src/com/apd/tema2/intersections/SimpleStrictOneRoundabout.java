package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

public class SimpleStrictOneRoundabout implements Intersection {

    String name;
    Integer maxCars, time;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getMaxCars() {
        return maxCars;
    }

    @Override
    public void setMaxCars(Integer maxCars) {
        this.maxCars = maxCars;
    }

    @Override
    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public Integer getTime() {
        return time;
    }

    @Override
    public Integer getMaxCarsLane() {
        return null;
    }

    @Override
    public void setMaxCarsLane(Integer maxCarsLane) {

    }

    @Override
    public void setHighPriorityCarsNo(Integer highPriorityCarsNo) {

    }

    @Override
    public void setLowPriorityCarsNo(Integer lowPriorityCarsNo) {

    }

    @Override
    public void setPedestrianTime(Integer pedestrianTime) {

    }

    @Override
    public void setMaxPedestriansNo(Integer maxPedestriansNo) {

    }

    @Override
    public void setCarsPassNo(Integer carsPassNo) {

    }

    @Override
    public Integer getHighPriorityCarsNo() {
        return null;
    }

    @Override
    public Integer getLowPriorityCarsNo() {
        return null;
    }
}
