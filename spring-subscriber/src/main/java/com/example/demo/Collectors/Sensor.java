package com.example.demo.Collectors;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.UUID;
@MappedSuperclass
public abstract class Sensor {

    @Id
    private UUID Id;

    private String sensorName;

    private String sensorType;

    private float data;
    protected Sensor() {

    }
    private LocalDateTime currentDateTime;
    @PrePersist
    protected void onCreate() {
        this.currentDateTime = LocalDateTime.now();
    }
    public Sensor(String sensorName, String sensorType, float data) {
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.data = data;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
