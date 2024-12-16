package com.volka.ecommerce.orderservice.entity;

import com.volka.ecommerce.orderservice.dto.OrderDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private Integer qty;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String orderId;


    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    protected Order() {}

    @Builder
    private Order(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String userId, String orderId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderId = orderId;
        this.createdAt = LocalDateTime.now();
    }

    public static Order create(OrderDto dto) {
        return Order.builder()
                .productId(dto.getProductId())
                .qty(dto.getQty())
                .unitPrice(dto.getUnitPrice())
                .totalPrice(dto.getTotalPrice())
                .userId(dto.getUserId())
                .orderId(dto.getOrderId())
                .build();
    }
}
