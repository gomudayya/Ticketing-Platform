package com.example.userservice.auth.filter;

import com.example.servicecommon.auth.UserRole;
import com.example.userservice.auth.dto.JwtClaimDto;
import com.example.userservice.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(JwtUtil.BEARER_PREFIX)) {
            String pureToken = authHeader.substring(7);

            if (!jwtUtil.validateToken(pureToken)) {
                return; // 유효하지 않으면 필터체인에 안걸고 메서드를 종료시킴. Controller 까지 못감
            }

            JwtClaimDto jwtClaimDto = jwtUtil.getJwtClaims(pureToken);
            setAuthInSecurityContext(jwtClaimDto);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthInSecurityContext(JwtClaimDto jwtClaimDto) {
        Long userId = jwtClaimDto.getId();
        UserRole userRole = jwtClaimDto.getUserRole();

//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userId, null, List.of(userRole::getAuthority));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
