package com.volka.ecommerce.orderservice.service;

import com.volka.ecommerce.orderservice.dto.OrderDto;
import com.volka.ecommerce.orderservice.entity.Order;
import com.volka.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        Order order = orderRepository.save(orderDto.toEntity());

        return OrderDto.of(order);
    }

    public OrderDto getOrderByOrderId(String orderId) {
        return OrderDto.of(orderRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("not found")));
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderDto::of)
                .toList();
    }
}
