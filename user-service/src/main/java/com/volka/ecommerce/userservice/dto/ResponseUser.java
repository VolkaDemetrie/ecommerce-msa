package com.volka.ecommerce.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    public ResponseUser(UserDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.userId = dto.getUserId();
    }

    public static ResponseUser of(UserDto dto) {
        return new ResponseUser(dto);
    }
}
