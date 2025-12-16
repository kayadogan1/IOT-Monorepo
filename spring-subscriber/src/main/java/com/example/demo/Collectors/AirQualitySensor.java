package com.example.demo.Collectors;

import jakarta.persistence.Entity;

@Entity
public class AirQualitySensor extends Sensor{

    public AirQualitySensor() {
        super();
    }
    public AirQualitySensor(String sensorName, String sensorType, float data) {
        super(sensorName, sensorType, data);
        this.setSensorName(sensorName);
        this.setSensorType(sensorType);
        this.setData(data);
    }

}
