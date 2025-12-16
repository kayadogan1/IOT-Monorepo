package com.example.demo.ElasticRepos;

import com.example.demo.Collectors.DistanceSensor;
import com.example.demo.Collectors.DistanceSensorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface DistanceSensorElasticRepo extends ElasticsearchRepository<DistanceSensorDocument, String > {

}
