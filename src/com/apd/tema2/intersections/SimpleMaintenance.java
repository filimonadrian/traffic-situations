package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

public class SimpleMaintenance implements Intersection {
    String name;
    Integer carsPassNo;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Integer getCarsPassNo() {
        return carsPassNo;
    }

    @Override
    public void setCarsPassNo(Integer carsPassNo) {
        this.carsPassNo = carsPassNo;
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
    public void setPedestrianTime(Integer pedestrianTime) {

    }

    @Override
    public void setMaxPedestriansNo(Integer maxPedestriansNo) {

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
