package com.ticketland.ticketland.global.exception.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_FORMAT(400, "올바르지 않은 입력포맷입니다."),

    VERIFY_EXPIRED(401, "인증이 만료되었습니다."),

    INVALID_VERIFY_CODE(400, "인증번호가 일치하지 않습니다."),

    EMAIL_NOT_VERIFIED(400, "이메일 인증이 되어있지 않습니다."),

    NOT_FOUND(404, "해당 %s을 찾을 수 없습니다."),

    ACCESS_DENIED(403, "권한이 부족합니다."),

    INVALID_ADMIN_KEY(401, "어드민 키가 올바르지 않습니다")

    ;

    private final int statusCode;
    private String msg;

    public void changeMsg(String msg) {
        this.msg = msg;
    }
}
