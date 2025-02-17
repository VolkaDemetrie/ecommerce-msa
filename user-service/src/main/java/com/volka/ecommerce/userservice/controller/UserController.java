package com.volka.ecommerce.userservice.controller;

import com.volka.ecommerce.userservice.dto.RequestUser;
import com.volka.ecommerce.userservice.dto.ResponseUser;
import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto dto = userService.getUserByUserId(userId);
        return ResponseEntity.ok(ResponseUser.of(dto));
    }

    @PostMapping
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser user) {
        UserDto dto = userService.createUser(UserDto.of(user));

        return new ResponseEntity<>(ResponseUser.of(dto), HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody RequestLogin loginDto) {
//        return null;
//    }

}
