package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

public class Crosswalk implements Intersection {
    String name;
    Integer pedestrianTime, maxPedestriansNo;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Integer getPedestrianTime() {
        return pedestrianTime;
    }

    @Override
    public void setPedestrianTime(Integer pedestrianTime) {
        this.pedestrianTime = pedestrianTime;
    }

    public Integer getMaxPedestriansNo() {
        return maxPedestriansNo;
    }

    @Override
    public void setMaxPedestriansNo(Integer maxPedestriansNo) {
        this.maxPedestriansNo = maxPedestriansNo;
    }

    @Override
    public void setTime(Integer time) {

    }

    @Override
    public void setMaxCars(Integer maxCars) {

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
    public void setCarsPassNo(Integer carsPassNo) {

    }

    @Override
    public Integer getMaxCars() {
        return null;
    }

    @Override
    public Integer getMaxCarsLane() {
        return null;
    }

    @Override
    public Integer getTime() {
        return null;
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
