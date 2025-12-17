package com.example.demo.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "ldr_sensor")
public class LdrSensorDocument {
    @Id
    private String Id;
    @Field(type = FieldType.Keyword)
    private String sensorName;
    @Field(type = FieldType.Keyword)
    private String sensorType;
    @Field(type = FieldType.Float)
    private float data;
    @Field(type= FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime currentDataTime;
    public LdrSensorDocument(String id,LocalDateTime currentDataTime, String sensorName, String sensorType, float data) {
        this.currentDataTime = currentDataTime;
        this.Id = id;
        this.data = data;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
    }
    public LdrSensorDocument() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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
