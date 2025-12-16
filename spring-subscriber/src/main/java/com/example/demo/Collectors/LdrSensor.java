package com.example.demo.Collectors;

import jakarta.persistence.Entity;

@Entity
public class LdrSensor extends Sensor{
    public LdrSensor() {
        super();
    }
    public LdrSensor(String sensorName, String sensorType, float data) {
        super(sensorName, sensorType, data);
        this.setSensorName(sensorName);
        this.setSensorType(sensorType);
        this.setData(data);
    }
}
