package com.example.demo.Repositories;

import com.example.demo.Collectors.DistanceSensor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistanceRepository extends JpaRepository<DistanceSensor, String> {

}
