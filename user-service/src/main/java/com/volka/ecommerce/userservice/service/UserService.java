package com.volka.ecommerce.userservice.service;

import com.volka.ecommerce.userservice.dto.ResponseOrder;
import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.entity.User;
import com.volka.ecommerce.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RestTemplate restTemplate;
    private final Environment env;

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
                .peek(userDto -> userDto.setOrders(
                        restTemplate.exchange(
                                String.format(env.getProperty("order-service.url"), userDto.getUserId())
                                , HttpMethod.GET
                                , null
                                , new ParameterizedTypeReference<List<ResponseOrder>>(){}
                        ).getBody()
                )).toList();
    }

    public UserDto getUserByUserId(String userId) {
        return UserDto.of(userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("not found user")));
    }


    public UserDto getUserDetailsByEmail(String email) {
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return UserDto.of(userEntity);
    }
}
