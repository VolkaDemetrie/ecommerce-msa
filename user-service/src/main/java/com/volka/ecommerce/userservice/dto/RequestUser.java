package com.volka.ecommerce.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUser(
        @NotNull(message = "Email cannot be null")
        @Size(min = 2, message = "Email not be less than two characters")
        @Email
        String email,
        @NotNull
        @Size(min = 2, message = "Name not be less than two characters")
        String name,
        @NotNull
        @Size(min = 4, message = "Password must be equal or greater than 8 characters")
        String pwd
){
//    @NotNull(message = "Email cannot be null")
//    @Size(min = 2, message = "Email not be less than two characters")
//    @Email
//    private String email;
//
//    @NotNull
//    @Size(min = 2, message = "Name not be less than two characters")
//    private String name;
//
//    @NotNull
//    @Size(min = 4, message = "Password must be equal or greater than 8 characters")
//    private String pwd;
}
