package com.example.servicecommon.resolver;

import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.constant.CustomHeader;
import com.example.servicecommon.dto.UserClaim;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserClaimArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        if (request.getHeader(CustomHeader.USER_ID) == null || request.getHeader(CustomHeader.USER_ROLE) == null) {
            return null;
        }

        Long userId = Long.valueOf(request.getHeader(CustomHeader.USER_ID));
        UserRole userRole = UserRole.valueOf(request.getHeader(CustomHeader.USER_ROLE));

        return new UserClaim(userId, userRole);
    }
}
