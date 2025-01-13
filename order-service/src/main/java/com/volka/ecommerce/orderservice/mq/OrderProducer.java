package com.volka.ecommerce.orderservice.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volka.ecommerce.orderservice.dto.OrderDto;
import com.volka.ecommerce.orderservice.dto.mq.Field;
import com.volka.ecommerce.orderservice.dto.mq.KafkaOrderDto;
import com.volka.ecommerce.orderservice.dto.mq.Payload;
import com.volka.ecommerce.orderservice.dto.mq.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final List<Field> fields = List.of(
            new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true, "qty"),
            new Field("int32", true, "total_price"),
            new Field("int32", true, "unit_price")
    );

    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderDto send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .order_id(orderDto.getOrderId())
                .product_id(orderDto.getProductId())
                .user_id(orderDto.getUserId())
                .qty(orderDto.getQty())
                .total_price(orderDto.getTotalPrice())
                .unit_price(orderDto.getUnitPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);
        String message = null;

        try {
            message = objectMapper.writer().writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(topic, message);
        log.info("Order Producer sent data from OrderService :: " + kafkaOrderDto);

        return orderDto;
    }
}
