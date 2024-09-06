package com.example.servicecommon.exception.core;

import com.example.servicecommon.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.ByteBuffer;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(new ObjectMapper());

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

    @Test
    @DisplayName("서비스간 통신에서 FeignException 이 발생하였을 때, 에러 바디를 올바르게 옮겨서 응답해야 한다.")
    void handleFeignException() throws JsonProcessingException {
        //given
        String errorBody = """
                {"statusCode":404,"errorCode":"NOT_FOUND","msg":"해당 공연을 찾을 수 없습니다."}""";
        byte[] bytes = errorBody.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        FeignException feignException = FeignException.errorStatus(
                "GET",
                feign.Response.builder()
                        .status(404)
                        .request(Request.create(Request.HttpMethod.GET, "/api/test", Collections.emptyMap(), null, null, null))
                        .body(byteBuffer.array()) // 에러 바디 설정
                        .build()
        );
        //when
        ResponseEntity<ExceptionResponse> response
                = globalExceptionHandler.handleFeignException(feignException);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(response.getBody());

        //then
        assertThat(responseBody).isEqualTo(errorBody);
    }
}