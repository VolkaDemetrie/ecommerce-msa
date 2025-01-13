package com.volka.ecommerce.orderservice.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Field {
    private String type;
    private boolean optional;
    private String field;
}
