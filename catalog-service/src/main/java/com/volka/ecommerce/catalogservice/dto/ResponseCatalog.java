package com.volka.ecommerce.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    public ResponseCatalog(CatalogDto dto) {
        this.productId = dto.getProductId();
        this.productName = dto.getProductName();
        this.stock = dto.getStock();
        this.unitPrice = dto.getUnitPrice();
        this.createdAt = dto.getCreatedAt();
    }

    public static ResponseCatalog of(CatalogDto dto) {
        return new ResponseCatalog(dto);
    }
}
