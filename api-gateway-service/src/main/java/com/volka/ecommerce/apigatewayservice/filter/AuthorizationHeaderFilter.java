package com.volka.ecommerce.apigatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private Environment environment;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.environment = env;
    }

    public static class Config {

    }

    /**
     * ServerWebExchange 의 request / response 는 immutable. 헤더 등을 수정하고 싶을땐 ServerHttpRequest.mutable()로 빌드하여 사용
     * response는 응답 커밋 전에 처리를 해줘야함. ServerHttpResponse.berforeCommit() 활용
     * @param config
     * @return
     */
    // login -> token -> users (with token) -> header(include token)
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

//            ServerHttpRequest mutateRequest = request.mutate().header("test-header", "test").build();
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
//            String authorizationHeader = mutateRequest.getHeaders().get(HttpHeaders.AUTHORIZATION).getFirst();
            String jwt = authorizationHeader.replace("Bearer ", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {

        boolean validated = true;

        byte[] secretKeyBytes = Base64.getEncoder().encode(environment.getProperty("token.secret").getBytes());
//        SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        log.debug("Jwts.SIG.HS512.getId() :: {}", Jwts.SIG.HS512.getId());
        SecretKey signingKey = Keys.hmacShaKeyFor(secretKeyBytes);

        String subject = null;

        try {
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(signingKey) // io.jsonwebtoken 에서 setSigningKey(SecretKey) deprecated 대체
                    .build();
            subject = jwtParser.parseSignedClaims(jwt).getPayload().getSubject();
        } catch (Exception e) {
            validated = false;
        }

        if (subject == null || subject.isBlank()) validated = false;

        return validated;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        log.error(error);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
//        response.beforeCommit(() -> {
//            response.setStatusCode(httpStatus);
//            return response.setComplete();
//        });

//        return Mono.empty();

//        byte[] bytes = "The requested token is invalid.".getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//        return response.writeWith(Flux.just(buffer));
    }


}
