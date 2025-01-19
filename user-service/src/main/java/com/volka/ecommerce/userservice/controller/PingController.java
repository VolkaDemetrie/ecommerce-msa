package com.volka.ecommerce.userservice.controller;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class PingController {

    private final Environment env;

    @GetMapping("/health-check")
    @Timed(value = "users.status", longTask = true)
    public String status(HttpServletRequest request) {
        return String.format(
                "It's Working in User Service \nport(local.server.port)=%s\nport(server.port)=%s\ntoken secret=%s\ntoken time=%s"
                ,env.getProperty("local.server.port")
                ,env.getProperty("server.port")
                ,env.getProperty("token.secret")
                ,env.getProperty("token.expiration-time")
        );
    }

    @GetMapping("/welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
        return env.getProperty("greeting.message");
    }
}
