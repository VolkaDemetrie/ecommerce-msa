package com.volka.ecommerce.userservice.service;

import com.volka.ecommerce.userservice.client.OrderServiceClient;
import com.volka.ecommerce.userservice.dto.ResponseOrder;
import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.entity.User;
import com.volka.ecommerce.userservice.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final Environment env;

    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format("%s: not found", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList<>() //권한
        );
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        User user = userRepository.save(userDto.toEntity());
        return UserDto.of(user);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::of)
                .peek(userDto -> {
                    List<ResponseOrder> orders = orderServiceClient.getOrders(userDto.getUserId());
                    userDto.setOrders(orders);
                }).toList();
    }

    public UserDto getUserByUserId(String userId) {
        UserDto userDto = userRepository.findByUserId(userId)
                .map(UserDto::of)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
//        List<ResponseOrder> orders = orderServiceClient.getOrders(userDto.getUserId());

        log.info("Before call order service");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        // 폴백 처리로 빈 리스트 반환
        List<ResponseOrder> orders = circuitBreaker.run(() -> orderServiceClient.getOrders(userId), throwable -> { log.error(throwable.getMessage()); return new ArrayList<>();});
        log.info("After call order service");
        userDto.setOrders(orders);

        return userDto;
    }


    public UserDto getUserDetailsByEmail(String email) {
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return UserDto.of(userEntity);
    }
}
