package com.volka.ecommerce.orderservice.dto;

import com.volka.ecommerce.orderservice.entity.Order;
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
    
    private ResponseOrder(Order order) {
        productId = order.getProductId();
        qty = order.getQty();
        unitPrice = order.getUnitPrice();
        totalPrice = order.getTotalPrice();
        orderId = order.getOrderId();
        createdAt = order.getCreatedAt();
    }

    public static ResponseOrder of(OrderDto orderDto) {
        return new ResponseOrder(orderDto);
    }
    public static ResponseOrder of(Order order) {
        return new ResponseOrder(order);
    }
}
