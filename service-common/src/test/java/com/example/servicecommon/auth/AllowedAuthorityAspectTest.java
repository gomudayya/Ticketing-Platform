package com.example.servicecommon.auth;

import com.example.servicecommon.constant.CustomHeader;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class AllowedAuthorityAspectTest {

    AllowedAuthorityAspect allowedAuthorityAspect = new AllowedAuthorityAspect();

    @Nested
    @DisplayName("유저 권한 체크 테스트")
    class checkUserAuthority {
        private MockedStatic<RequestContextHolder> setupRequestContextHolderWithMockRequest(HttpServletRequest mockRequest) {
            ServletRequestAttributes requestAttributes = mock(ServletRequestAttributes.class);
            when(requestAttributes.getRequest()).thenReturn(mockRequest);

            MockedStatic<RequestContextHolder> mockStatic = mockStatic(RequestContextHolder.class);
            mockStatic.when(RequestContextHolder::currentRequestAttributes).thenReturn(requestAttributes);

            return mockStatic;
        }

        @Test
        @DisplayName("헤더에 들어온 Role 정보가 없다면 예외가 발생한다.")
        void test1() {
            //given
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader(CustomHeader.USER_ROLE)).thenReturn(null);
            MockedStatic<RequestContextHolder> requestContextHolder = setupRequestContextHolderWithMockRequest(request);

            //when, then
            assertThatThrownBy(() -> allowedAuthorityAspect.checkUserAuthority(mock(JoinPoint.class), mock(AllowedAuthority.class)))
                    .isInstanceOf(CustomAccessDeniedException.class);

            requestContextHolder.close(); // 종료 해줘야 다른메서드에서 안깨짐.
        }


        @Test
        @DisplayName("허용되지 않은 권한이면 예외가 발생한다.")
        void test2() {
            //given
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader(CustomHeader.USER_ROLE)).thenReturn("USER");
            MockedStatic<RequestContextHolder> mockStatic = setupRequestContextHolderWithMockRequest(request);

            AllowedAuthority allowedAuthority = mock(AllowedAuthority.class);
            when(allowedAuthority.value()).thenReturn(new String[]{UserRole.Authority.ADMIN});

            //when, then
            assertThatThrownBy(() -> allowedAuthorityAspect.checkUserAuthority(mock(JoinPoint.class), allowedAuthority))
                    .isInstanceOf(CustomAccessDeniedException.class);

            mockStatic.close();
        }

        @Test
        @DisplayName("허용된 권한이면 예외가 발생하지 않는다.")
        void test3() {
            //given
            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader(CustomHeader.USER_ROLE)).thenReturn("ADMIN");
            MockedStatic<RequestContextHolder> mockStatic = setupRequestContextHolderWithMockRequest(request);

            AllowedAuthority allowedAuthority = mock(AllowedAuthority.class);
            when(allowedAuthority.value()).thenReturn(new String[]{UserRole.Authority.ADMIN});

            //when, then
            allowedAuthorityAspect.checkUserAuthority(mock(JoinPoint.class), allowedAuthority);

            mockStatic.close();
        }
    }

}