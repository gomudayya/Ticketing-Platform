package com.example.servicecommon.auth;

import com.example.servicecommon.constant.CustomHeader;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
@Slf4j
public class AllowedAuthorityAspect {

    @Before("@annotation(allowedAuthority)")
    public void checkUserAuthority(JoinPoint joinPoint, AllowedAuthority allowedAuthority) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String roleHeader = request.getHeader(CustomHeader.USER_ROLE);
        if (roleHeader == null) {
            log.info("Access denied: No role header found in the request.");
            throw new CustomAccessDeniedException();
        }

        List<String> userAuthorities = UserRole.valueOf(request.getHeader(CustomHeader.USER_ROLE)).getAuthorities();
        for (String allowedAuth : allowedAuthority.value()) {
            if (userAuthorities.contains(allowedAuth)) {
                return;
            }
        }

        throw new CustomAccessDeniedException();
    }
}
