package com.volka.ecommerce.secondservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/second-service")
@RestController
public class SecondController {

    private final Environment env;

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        System.out.println(header);
        return "Hello World in Second Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());
        return String.format("Hi, there. Second service on PORT %s", env.getProperty("local.server.port"));
    }
}
