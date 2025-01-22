package com.volka.ecommerce.catalogservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "catalogs")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false, updatable = false, insertable = false)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    protected Catalog() {}

    @Builder
    private Catalog(String productId, String productName, Integer stock, Integer unitPrice, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
    }

    public static Catalog create(String productId, String productName, Integer stock, Integer unitPrice) {
        return Catalog.builder()
                .productId(productId)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitPrice)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void reduce(@Positive Integer qty) {
        this.stock -= qty;
    }
}
