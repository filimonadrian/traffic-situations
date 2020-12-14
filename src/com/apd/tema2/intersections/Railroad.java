package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

public class Railroad implements Intersection {
    String name;
    Integer time, maxCars, maxCarsLane;
    Integer highPriorityCarsNo, lowPriorityCarsNo;
    Integer pedestrianTime, maxPedestriansNo;
    Integer carsPassNo, lanesNo;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getTime() {
        return time;
    }

    @Override
    public void setTime(Integer time) {
        this.time = time;
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
    public Integer getMaxCarsLane() {
        return maxCarsLane;
    }

    @Override
    public void setMaxCarsLane(Integer maxCarsLane) {
        this.maxCarsLane = maxCarsLane;
    }

    @Override
    public Integer getHighPriorityCarsNo() {
        return highPriorityCarsNo;
    }

    @Override
    public void setHighPriorityCarsNo(Integer highPriorityCarsNo) {
        this.highPriorityCarsNo = highPriorityCarsNo;
    }

    @Override
    public Integer getLowPriorityCarsNo() {
        return lowPriorityCarsNo;
    }

    @Override
    public void setLowPriorityCarsNo(Integer lowPriorityCarsNo) {
        this.lowPriorityCarsNo = lowPriorityCarsNo;
    }

    @Override
    public Integer getPedestrianTime() {
        return pedestrianTime;
    }

    @Override
    public void setPedestrianTime(Integer pedestrianTime) {
        this.pedestrianTime = pedestrianTime;
    }

    @Override
    public Integer getMaxPedestriansNo() {
        return maxPedestriansNo;
    }

    @Override
    public void setMaxPedestriansNo(Integer maxPedestriansNo) {
        this.maxPedestriansNo = maxPedestriansNo;
    }

    @Override
    public Integer getCarsPassNo() {
        return carsPassNo;
    }

    @Override
    public void setCarsPassNo(Integer carsPassNo) {
        this.carsPassNo = carsPassNo;
    }

    @Override
    public Integer getLanesNo() {
        return lanesNo;
    }

    @Override
    public void setLanesNo(Integer lanesNo) {
        this.lanesNo = lanesNo;
    }
}
