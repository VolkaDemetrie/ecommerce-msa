package com.volka.ecommerce.userservice.controller;

import com.volka.ecommerce.userservice.dto.RequestUser;
import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody RequestUser user) {

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        userService.createUser(userDto);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
