package com.volka.ecommerce.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestLogin {

    @NotNull
    @Size(min = 2)
    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
