package com.example.servicecommon.exception.core;

import com.example.servicecommon.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("CustomException 을 잡을때 때 에러코드의 메시지와, 예외 메시지가 다를경우에는 예외 메시지가 응답값으로 반환된다.")
    void handleCustomException() {
        //given
        ErrorCode errorCode = ErrorCode.NOT_FOUND;
        String exceptionMessage = errorCode.getMsg() + "difference";

        NotFoundException notFoundException = mock(NotFoundException.class);
        given(notFoundException.getErrorCode()).willReturn(errorCode);
        given(notFoundException.getMessage()).willReturn(exceptionMessage);

        //when
        ResponseEntity<ExceptionResponse> exceptionResponse =
                globalExceptionHandler.handleCustomException(notFoundException);

        //then
        assertThat(exceptionResponse.getBody().getMsg()).isEqualTo(exceptionMessage);
    }
}