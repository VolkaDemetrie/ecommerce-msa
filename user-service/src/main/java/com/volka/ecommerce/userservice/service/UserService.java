package com.volka.ecommerce.userservice.service;

import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.entity.User;
import com.volka.ecommerce.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        User user = userRepository.save(userDto.toEntity());
        return UserDto.of(user);
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserDto::of).toList();
    }
}
