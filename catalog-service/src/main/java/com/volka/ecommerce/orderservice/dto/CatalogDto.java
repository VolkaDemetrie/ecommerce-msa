package com.volka.ecommerce.orderservice.dto;

import com.volka.ecommerce.orderservice.entity.Catalog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CatalogDto implements Serializable {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String productName;
    private Integer stock;
    private LocalDateTime createdAt;

    private String orderId;
    private String userId;

    public CatalogDto(Catalog catalog) {
        this.productId = catalog.getProductId();
        this.unitPrice = catalog.getUnitPrice();
        this.stock = catalog.getStock();
        this.productName = catalog.getProductName();
        this.createdAt = catalog.getCreatedAt();
    }

    public static CatalogDto of(Catalog catalog) {
        return new CatalogDto(catalog);
    }
}
