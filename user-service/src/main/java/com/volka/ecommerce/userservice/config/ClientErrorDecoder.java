package com.volka.ecommerce.userservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClientErrorDecoder implements ErrorDecoder {

    private final Environment env;

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
            case 404:
//                // FIXME :: 테스트용.
//                if (methodKey.contains("getOrders")) {
//                    return new ResponseStatusException(HttpStatus.valueOf(response.status())
//                            , "User's orders is empty");
//                }
//                break;
            default:
                log.error("Feign Client ERROR :: {} : {}", response.status(), response.reason());
//                return new Exception(response.reason());
                return new ResponseStatusException(HttpStatus.valueOf(response.status()));
        }
    }
}
