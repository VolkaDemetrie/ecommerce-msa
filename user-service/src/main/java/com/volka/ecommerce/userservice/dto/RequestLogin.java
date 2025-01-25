package com.volka.ecommerce.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestLogin (

        @NotNull
        @Size(min = 2)
        @Email
        String email,
        @NotNull
        @Size(min = 8)
        String password
) {

}
