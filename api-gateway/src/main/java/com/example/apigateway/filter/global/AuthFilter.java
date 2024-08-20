package com.example.apigateway.filter.global;

import com.example.apigateway.dto.JwtClaimDto;
import com.example.apigateway.util.MonoUtil;
import com.example.apigateway.util.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final JwtUtil jwtUtil;

    public AuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> { // Mono<Void> 반환 -> Mono<Void> : Webflux 기본단위
            ServerHttpRequest request = exchange.getRequest(); // Webflux 에서는 서블릿 기반의 객체들을 사용하지 않음.
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return chain.filter(exchange); // Authorization 헤더가 없으면 그냥 필터에 건다.
            }

            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader.startsWith(JwtUtil.BEARER_PREFIX)) {
                String pureToken = authHeader.substring(7);

                if (!jwtUtil.validateToken(pureToken)) {
                    return MonoUtil.onError(exchange, "유효하지 않은 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED);
                }

                JwtClaimDto jwtClaims = jwtUtil.getJwtClaims(pureToken);

                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", String.valueOf(jwtClaims.getId()))
                        .header("X-User-Role", jwtClaims.getUserRole().name())
                        .build();

                exchange = exchange.mutate().request(modifiedRequest).build();
            }

            return chain.filter(exchange);
        });
    }

    @Data
    public static class Config {}
}
