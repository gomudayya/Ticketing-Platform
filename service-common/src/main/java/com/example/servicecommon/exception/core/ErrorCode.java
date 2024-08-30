package com.example.servicecommon.exception.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버에서 예기치 못한 에러가 발생하였습니다."),

    NOT_SUPPORTED_HTTP_METHOD(400, "현재 API에서 지원하지 않는 HTTP 메서드 입니다."),

    NOT_SUPPORTED_CONTENT_TYPE(400, "현재 API에서 지원하지 않는 content-type 입니다."),

    INVALID_INPUT_FORMAT(400, "올바르지 않은 입력포맷입니다."),

    VERIFY_EXPIRED(401, "인증이 만료되었습니다."),

    INVALID_VERIFY_CODE(400, "인증번호가 일치하지 않습니다."),

    EMAIL_NOT_VERIFIED(400, "이메일 인증이 되어있지 않습니다."),

    DUPLICATED_EMAIL(409, "이미 가입된 이메일입니다."),

    NOT_FOUND(404, "해당 %s을 찾을 수 없습니다."),

    ACCESS_DENIED(403, "권한이 부족합니다."),

    INVALID_ADMIN_KEY(401, "어드민 키가 올바르지 않습니다"),

    TICKET_SALE_NOT_ACTIVE(400, "현재는 티켓 판매 시간이 아닙니다"),

    SEAT_SECTION_UNMATCHED(400, "좌석 구획 정보가 실제 좌석 정보와 일치하지 않습니다."),

    TICKET_REFUND_NOT_ACTIVE(400, "현재는 환불 가능 시간이 아닙니다."),

    TICKET_UNAVAILABLE(409, "해당 티켓은 이미 선택되었거나, 구매된 티켓입니다."),

    INVALID_PAYMENT(400, "결제 정보가 올바르지 않습니다."),

    ;

    private final int statusCode;
    private final String msg;
}
