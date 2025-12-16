package com.example.demo.Services;
import com.example.demo.Collectors.AirQualitySensorDocument;
import com.example.demo.Collectors.DistanceSensorDocument;
import com.example.demo.Collectors.LdrSensorDocument;
import com.example.demo.Collectors.Sensor;

public final   class SensorMapper {
    public SensorMapper() {

    }
    public static Object toDocument(Sensor sensor) {
        if( sensor==null ) return null;
        return switch (sensor.getSensorType()) {
            case "AirQuality", "Hava" ->
                    new AirQualitySensorDocument(sensor.getId().toString(), sensor.getCurrentDateTime(), sensor.getSensorName(), sensor.getSensorType(), sensor.getData());
            case "Ldr", "Isik" ->
                    new LdrSensorDocument(sensor.getId().toString(), sensor.getCurrentDateTime(), sensor.getSensorName(), sensor.getSensorType(), sensor.getData());
            case "Distance", "Mesafe" ->
                    new DistanceSensorDocument(sensor.getId().toString(), sensor.getCurrentDateTime(), sensor.getSensorName(), sensor.getSensorType(), sensor.getData());
            default -> throw new IllegalArgumentException("Sensor type not supported" + sensor.getSensorType());
        };

    }
}
