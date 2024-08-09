package com.ticketland.ticketland.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailVerifyDto {
    @Email
    @NotBlank
    private String email;

    @NotNull
    private Integer verifyCode;
}
