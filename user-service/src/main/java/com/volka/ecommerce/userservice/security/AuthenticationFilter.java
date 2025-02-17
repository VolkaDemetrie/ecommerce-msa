package com.volka.ecommerce.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volka.ecommerce.userservice.dto.RequestLogin;
import com.volka.ecommerce.userservice.dto.UserDto;
import com.volka.ecommerce.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment environment;
    private final ObjectMapper objectMapper;
    private final Validator validator;
//    private SecretKey secretKey;

//    private static final String algorithm = "HmacSHA256";

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment environment, Validator validator) throws NoSuchAlgorithmException {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
        this.objectMapper = new ObjectMapper();
//        secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
        this.validator = validator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = objectMapper.reader().readValue(request.getInputStream(), RequestLogin.class);

            Errors errors = validator.validateObject(creds);
            if (errors.hasErrors()) throw new RuntimeException(errors.getAllErrors().toString());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.email(), creds.password(), new ArrayList<>()) // 배열은 권한
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult
    ) {
        log.info("authResult :: {}", authResult);
        User userDetails = (User) authResult.getPrincipal();
        String userName = userDetails.getUsername();
        UserDto userDto = userService.getUserDetailsByEmail(userName);

        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
//        SecretKey secretKey = keyFactory.generateSecret();

        Instant now = Instant.now();

        String token = Jwts.builder()
                .subject(userDto.getUserId())
                .expiration(Date.from(now.plusMillis(Long.parseLong(environment.getProperty("token.expiration-time")))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
//                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
    }

//    private SecretKey generateSecretKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        return SecretKeyFactory.getInstance(algorithm).generateSecret(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
//    }

//    public static String Hmac(String key, String message, String algorithm) throws Exception {
//        Mac hasher = Mac.getInstance(algorithm);
//        hasher.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
//
//        byte[] hash = hasher.doFinal(message.getBytes());
//
//        return Base64.encodeBase64String(hash);
//    }
}
