package com.volka.ecommerce.userservice.entity;

import com.volka.ecommerce.userservice.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Getter
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String encryptedPwd;

    @CreatedDate
    private LocalDateTime createdAt;

    protected User() {}

    @Builder
    private User(String userId, String email, String name, String encryptedPwd, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.encryptedPwd = encryptedPwd;
        this.createdAt = createdAt;
    }

    public static User create(UserDto userDto) {
        return User.builder()
                .userId(userDto.getUserId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .encryptedPwd(userDto.getEncryptedPwd())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
