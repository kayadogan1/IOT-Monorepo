package com.example.demo.mqttListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    private static final Logger logger= LoggerFactory.getLogger(MqttConfig.class);

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id}")
    private String clientId;

    @Bean
    public MessageChannel mqttChannel() {
        return new DirectChannel();
    }


    @Bean
    public MessageProducer inbound() {

        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(brokerUrl,clientId,"sensor/data");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttChannel());
        return adapter;
   }

   @Bean
   @ServiceActivator(inputChannel = "mqttChannel")
    public MessageHandler handler() {
        return message -> {
            String topic =message.getHeaders().get("mqtt_receivedTopic").toString();
            String payload =message.getPayload().toString();

            logger.info("-----------------------------------");
            logger.info("Received new a message from topic");
            logger.info("topic:{}",topic);
            logger.info("payload:{}",payload);
            logger.info("-----------------------------------");
        };
   }
}
