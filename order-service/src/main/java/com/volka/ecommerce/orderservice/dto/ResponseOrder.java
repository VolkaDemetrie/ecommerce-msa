package com.volka.ecommerce.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    private String orderId;

    private ResponseOrder(OrderDto orderDto) {
        productId = orderDto.getProductId();
        qty = orderDto.getQty();
        unitPrice = orderDto.getUnitPrice();
        totalPrice = orderDto.getTotalPrice();
        orderId = orderDto.getOrderId();
    }

    public static ResponseOrder of(OrderDto orderDto) {
        return new ResponseOrder(orderDto);
    }
}
