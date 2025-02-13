package com.apd.tema2.entities;

/**
 * Utilizata pentru a uniformiza tipul de date ce ajuta la definirea unei intersectii / a unui task.
 * Implementarile acesteia vor contine variabile specifice task-ului, respectiv mecanisme de sincronizare.
 */
public interface Intersection {

    void setName(String name);
    void setTime(Integer time);
    void setMaxCars(Integer maxCars);
    void setMaxCarsLane(Integer maxCarsLane);
    void setHighPriorityCarsNo(Integer highPriorityCarsNo);
    void setLowPriorityCarsNo(Integer lowPriorityCarsNo);
    void setPedestrianTime(Integer pedestrianTime);
    void setMaxPedestriansNo(Integer maxPedestriansNo);
    void setCarsPassNo(Integer carsPassNo);
    void setLanesNo(Integer lanesNo);

    String getName();
    Integer getTime();
    Integer getMaxCars();
    Integer getMaxCarsLane();
    Integer getHighPriorityCarsNo();
    Integer getLowPriorityCarsNo();
    Integer getPedestrianTime();
    Integer getMaxPedestriansNo();
    Integer getCarsPassNo();
    Integer getLanesNo();


}
