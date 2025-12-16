package com.example.demo.mqttListener;
import com.example.demo.Collectors.*;
import com.example.demo.ElasticRepos.AirQualitySensorElasticRepo;
import com.example.demo.ElasticRepos.DistanceSensorElasticRepo;
import com.example.demo.ElasticRepos.LdrSensorElasticRepo;
import com.example.demo.Repositories.AirQualitySensorRepository;
import com.example.demo.Repositories.DistanceRepository;
import com.example.demo.Repositories.LdrSensorRepository;
import com.example.demo.Services.SensorMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@Configuration
public class MqttConfig {

    private static final Logger logger = LoggerFactory.getLogger(MqttConfig.class);

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;
    @Autowired
    AirQualitySensorElasticRepo airQualitySensorElasticRepo;

    @Autowired
    LdrSensorElasticRepo ldrSensorElasticRepo;

    @Autowired
    DistanceSensorElasticRepo distanceSensorElasticRepo;

    @Autowired
    DistanceRepository distanceRepo;

    @Autowired
    LdrSensorRepository ldrSensorRepository;

    @Autowired
    AirQualitySensorRepository airQualitySensorRepository;
    @Bean
    public MessageChannel mqttChannel() {
        return new DirectChannel();
    }
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        brokerUrl,
                        clientId,
                        "sensor/data"
                );

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttChannel());

        logger.info("MQTT inbound adapter initialized. Broker={}, Topic=sensor/data", brokerUrl);

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttChannel")
    public MessageHandler handler() {
        return message -> {
                String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
                String payload = message.getPayload().toString();

                logger.info("-----------------------------------");
                logger.info("Received new a message from topic");
                logger.info("topic:{}", topic);
                logger.info("payload:{}", payload);
                logger.info("-----------------------------------");

                var sensor =SensorProcessor(payload);
                if (sensor == null) {
                    logger.error("Sensor could not be processed. Payload={}", payload);
                    return;
                }

                UUID commonId = UUID.randomUUID();
                sensor.setId(commonId);
                sensor.setCurrentDateTime(LocalDateTime.now());
                var sensorDocument =  SensorMapper.toDocument(sensor);
                SaveToDatabase(sensor);
            };
    }
    public void SaveToDatabase(Sensor sensor) {

        Object documentObj = SensorMapper.toDocument(sensor);
        logger.info("Saving to database"+ documentObj.toString());
        if (sensor instanceof AirQualitySensor airSensor) {
            airQualitySensorRepository.save(airSensor);

            if (documentObj instanceof AirQualitySensorDocument airDoc) {
                airQualitySensorElasticRepo.save(airDoc);
            }
        }
        else if (sensor instanceof DistanceSensor distSensor) {
            distanceRepo.save(distSensor);

            if (documentObj instanceof DistanceSensorDocument distDoc) {
                distanceSensorElasticRepo.save(distDoc);
            }
        }
        else if (sensor instanceof LdrSensor ldrSensor) {
            ldrSensorRepository.save(ldrSensor);

            if (documentObj instanceof LdrSensorDocument ldrDoc) {
                ldrSensorElasticRepo.save(ldrDoc);
            }
        }
    }
    public Sensor SensorProcessor(String payload) {
        try{
            JsonNode rootNode = objectMapper.readTree(payload);
            if(!rootNode.has("sensorType")){
                logger.error("Sensor type not found");
                return null;
            }
            String type = rootNode.get("sensorType").asText();
            return switch (type) {
                case "AirQuality", "Hava" -> objectMapper.treeToValue(rootNode, AirQualitySensor.class);
                case "Distance", "Mesafe" -> objectMapper.treeToValue(rootNode, DistanceSensor.class);
                case "Ldr", "IsikSensor" -> objectMapper.treeToValue(rootNode, LdrSensor.class);
                default -> {
                    logger.error("Sensor type not found {}", type);
                    yield null;
                }
            };

        }catch (JsonProcessingException jsonProcessingException){
            logger.error("Json could not parse {}", jsonProcessingException.getMessage());
            return null;
        }
    }
}
