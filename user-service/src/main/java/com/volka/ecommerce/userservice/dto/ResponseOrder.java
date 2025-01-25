package com.volka.ecommerce.userservice.dto;

import java.util.Date;

public record ResponseOrder(
    String productId,
    String orderId,
    Integer qty,
    Integer unitPrice,
    Integer totalPrice,
    Date createdAt
) {

}
