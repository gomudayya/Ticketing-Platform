package com.example.servicecommon.resolver;

import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.constant.CustomHeader;
import com.example.servicecommon.dto.UserClaim;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserClaimArgumentResolverTest {

    UserClaimArgumentResolver userClaimArgumentResolver = new UserClaimArgumentResolver();
    @Nested
    @DisplayName("유저 정보 ArgumentResolve 테스트")
    class resolveArgument {

        @Test
        @DisplayName("요청 헤더에 UserId가 존재하지 않거나, UserRole이 존재하지 않는 경우 예외가 발생해야 한다.")
        void test1() {
            //given
            HttpServletRequest servletRequest = mock(HttpServletRequest.class);
            given(servletRequest.getHeader(CustomHeader.USER_ID)).willReturn(null);

            NativeWebRequest request = mock(NativeWebRequest.class);
            given(request.getNativeRequest()).willReturn(servletRequest);

            //when. then
            assertThatThrownBy(() ->
                    userClaimArgumentResolver.resolveArgument(
                    mock(MethodParameter.class),
                    mock(ModelAndViewContainer.class),
                    request,
                    mock(WebDataBinderFactory.class))).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("요청헤더에 들어온 userId와 userRole 값을 바탕으로 UserClaim 객체를 반환해야 한다.")
        void test2() {
            //given
            String userId = "311";
            String userRole = "USER";

            HttpServletRequest servletRequest = mock(HttpServletRequest.class);
            given(servletRequest.getHeader(CustomHeader.USER_ID)).willReturn(userId);
            given(servletRequest.getHeader(CustomHeader.USER_ROLE)).willReturn(userRole);

            NativeWebRequest request = mock(NativeWebRequest.class);
            given(request.getNativeRequest()).willReturn(servletRequest);

            //when
            Object result = userClaimArgumentResolver.resolveArgument(
                    mock(MethodParameter.class),
                    mock(ModelAndViewContainer.class),
                    request,
                    mock(WebDataBinderFactory.class));

            //then
            assertThat(result).isInstanceOf(UserClaim.class);
            UserClaim userClaim = (UserClaim) result;
            assert userClaim != null;
            assertThat(userClaim.getUserId()).isEqualTo(Long.valueOf(userId));
            assertThat(userClaim.getRole()).isEqualTo(UserRole.valueOf(userRole));
        }
    }
}