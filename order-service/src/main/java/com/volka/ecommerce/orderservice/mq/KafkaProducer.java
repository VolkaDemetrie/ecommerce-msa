package com.volka.ecommerce.orderservice.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 카프카 컨슈머
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {


    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {

    }
}
