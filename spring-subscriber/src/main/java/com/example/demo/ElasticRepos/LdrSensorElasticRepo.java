package com.example.demo.ElasticRepos;

import com.example.demo.Collectors.LdrSensorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface LdrSensorElasticRepo extends ElasticsearchRepository<LdrSensorDocument, String> {



}
