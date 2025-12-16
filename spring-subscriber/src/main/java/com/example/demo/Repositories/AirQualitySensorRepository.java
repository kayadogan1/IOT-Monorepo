package com.example.demo.Repositories;

import com.example.demo.Collectors.AirQualitySensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirQualitySensorRepository extends JpaRepository<AirQualitySensor, String> {

}
