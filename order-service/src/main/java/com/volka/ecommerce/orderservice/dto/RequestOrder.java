package com.volka.ecommerce.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
//    private Integer totalPrice;
//    private LocalDateTime createdAt;
//
//    private String orderId;
}
