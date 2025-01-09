package com.volka.ecommerce.orderservice.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.volka.ecommerce.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 카프카 프로듀서
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderDto send(String topic, OrderDto orderDto) {
        String jsonStringify = null;
        try {
            jsonStringify = objectMapper.writer().writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            log.error("json processing error");
        }

        kafkaTemplate.send(topic, jsonStringify);
        log.info("Kafka Producer sent data from the OrderService: {}", orderDto);

        return orderDto;
    }
}
