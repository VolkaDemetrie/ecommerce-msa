package com.volka.ecommerce.orderservice.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class KafkaOrderDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
