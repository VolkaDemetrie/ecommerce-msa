package com.volka.ecommerce.userservice.controller;

import com.volka.ecommerce.userservice.dto.RequestUser;
import com.volka.ecommerce.userservice.dto.ResponseUser;
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

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto dto = userService.getUserByUserId(userId);
        return ResponseEntity.ok(ResponseUser.of(dto));
    }

    @PostMapping
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        UserDto dto = userService.createUser(userDto);

        return new ResponseEntity<>(ResponseUser.of(dto), HttpStatus.CREATED);
    }

}
