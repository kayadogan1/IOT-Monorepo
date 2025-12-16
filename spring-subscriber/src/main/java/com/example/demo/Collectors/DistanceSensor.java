package com.example.demo.Collectors;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
@Entity
public class DistanceSensor extends Sensor{

    private BigDecimal calculatedAmoundOfConsumedWater;
    public DistanceSensor() {
        super();
    }
    public DistanceSensor(String sensorName, String sensorType, float data, BigDecimal calculatedAmoundOfConsumedWater) {
        super(sensorName, sensorType, data);
        this.setSensorName(sensorName);
        this.setSensorType(sensorType);
        this.setData(data);
        this.calculatedAmoundOfConsumedWater = calculatedAmoundOfConsumedWater;
    }
    public BigDecimal GetCalculatedAmoundOfConsumedWater() {
        return calculatedAmoundOfConsumedWater;
    }
}
