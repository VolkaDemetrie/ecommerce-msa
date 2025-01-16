package com.volka.ecommerce.userservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4JConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfig() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) // CircuitBreaker를 open할지 결정하는 failure rate threshold percentage. 기본 50
                .waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker open 유지 시간. 이 기간 이후 half-open 상태. 기본 60초
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // 서킷 브레키어가 close 될 때 통화 결과를 기록하는데 사용되는 슬라이딩 창의 유형 : 카운트 기반 / 시간 기반
                .slidingWindowSize(2) // 서킷 브레이커가 닫힐 때 호출 결과를 기록하는데 사용되는 슬라이딩 창의 크기를 구성. 디폴트 100
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4L)) // TimeLimiter는 future supplier의 time limit을 정하는 API. 기본 1초
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build()
        );
    }
}
