package com.example.demo.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "distance_sensor")
public class DistanceSensorDocument {
    @Id
    private String Id;
    private String sensorName;
    private String sensorType;
    private float  data;
    @Field(type =FieldType.Date , format = DateFormat.date_time) 
    private LocalDateTime currentDataTime;

    public DistanceSensorDocument(String id,LocalDateTime createdTime ,String sensorName, String sensorType, float data) {
        this.Id = id;
        this.currentDataTime = createdTime;
        this.sensorName = sensorName;
        this.data= data;
        this.sensorType = sensorType;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public DistanceSensorDocument() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String  id) {
        Id = id;
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

    public LocalDateTime getCurrentDataTime() {
        return currentDataTime;
    }

    public void setCurrentDataTime(LocalDateTime currentDataTime) {
        this.currentDataTime = currentDataTime;
    }
}
