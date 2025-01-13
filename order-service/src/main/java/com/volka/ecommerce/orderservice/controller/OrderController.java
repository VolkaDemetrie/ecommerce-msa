package com.volka.ecommerce.orderservice.controller;

import com.volka.ecommerce.orderservice.dto.OrderDto;
import com.volka.ecommerce.orderservice.dto.RequestOrder;
import com.volka.ecommerce.orderservice.dto.ResponseOrder;
import com.volka.ecommerce.orderservice.mq.KafkaProducer;
import com.volka.ecommerce.orderservice.mq.OrderProducer;
import com.volka.ecommerce.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class OrderController {
    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health-check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in User Service on Port %s", request.getServerPort());
    }

    @GetMapping("/welcome")
    public String welcome() {
        return env.getProperty("greeting.message");
    }

    @GetMapping
    public ResponseEntity<List<ResponseOrder>> getAll() {
        return null;
    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @PathVariable("userId") String userId
            ,@RequestBody RequestOrder requestOrder
    ) {
        OrderDto orderDto = OrderDto.of(requestOrder);
        orderDto.setUserId(userId);
        /* JPA */
//        OrderDto createdOrder = orderService.createOrder(orderDto);

        /* Kafka */
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(requestOrder.getQty() * requestOrder.getUnitPrice());

        kafkaProducer.send("example-catalog-topic", orderDto); // 연결 도메인 서비스 싱크
        orderProducer.send("orders", orderDto); // DB 저장

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseOrder.of(orderDto));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> getOrderById(@PathVariable("orderId") String orderId) {
        return ResponseEntity.ok(ResponseOrder.of(orderService.getOrderByOrderId(orderId)));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getAllByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId).stream()
                .map(ResponseOrder::of)
                .toList());
    }
}
