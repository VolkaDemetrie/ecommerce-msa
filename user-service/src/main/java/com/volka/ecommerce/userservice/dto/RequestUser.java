package com.volka.ecommerce.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull
    @Size(min = 8, message = "Password must be equal or greater than 8 characters")
    private String pwd;
}
