package com.example.demo.Repositories;

import com.example.demo.Collectors.LdrSensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LdrSensorRepository extends JpaRepository<LdrSensor, String> {
}
