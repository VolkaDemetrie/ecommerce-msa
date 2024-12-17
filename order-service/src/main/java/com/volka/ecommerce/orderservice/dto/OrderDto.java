package com.volka.ecommerce.orderservice.dto;

import com.volka.ecommerce.orderservice.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class OrderDto implements Serializable {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    private OrderDto(Order order) {
        productId = order.getProductId();
        qty = order.getQty();
        unitPrice = order.getUnitPrice();
        totalPrice = order.getTotalPrice();
        orderId = order.getOrderId();
        userId = order.getUserId();
    }

    private OrderDto(RequestOrder requestOrder) {
        productId = requestOrder.getProductId();
        qty = requestOrder.getQty();
        unitPrice = requestOrder.getUnitPrice();
//        totalPrice = requestOrder.getTotalPrice();
//        orderId = requestOrder.getOrderId();
//        userId = requestOrder.getUserId();
    }

    public static OrderDto of(Order order) {
        return new OrderDto(order);
    }
    public static OrderDto of(RequestOrder requestOrder) {
        return new OrderDto(requestOrder);
    }

    public Order toEntity() {
        return Order.create(this);
    }
}
