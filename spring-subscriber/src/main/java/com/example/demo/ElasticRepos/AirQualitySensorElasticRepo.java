package com.example.demo.ElasticRepos;

import com.example.demo.Collectors.AirQualitySensor;
import com.example.demo.Collectors.AirQualitySensorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface AirQualitySensorElasticRepo  extends ElasticsearchRepository<AirQualitySensorDocument, String> {
}
