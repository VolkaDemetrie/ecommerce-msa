package com.volka.ecommerce.userservice.dto;

import com.volka.ecommerce.userservice.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UserDto {
    @Email
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String userId;
    @NotNull
    private String encryptedPwd;

    private LocalDateTime createdAt;


    public User toEntity() {
        return User.create(this);
    }

    private UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.encryptedPwd = user.getEncryptedPwd();
        this.userId = user.getUserId();
        this.createdAt = user.getCreatedAt();
    }

    public static UserDto of(User user) {
        return new UserDto(user);
    }
}
