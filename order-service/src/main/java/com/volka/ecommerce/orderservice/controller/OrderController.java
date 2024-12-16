package com.volka.ecommerce.orderservice.controller;

import com.volka.ecommerce.orderservice.dto.ResponseOrder;
import com.volka.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {
    private final Environment env;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<ResponseOrder>> getAll() {
        return null;
    }
}
