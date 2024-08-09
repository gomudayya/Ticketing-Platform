package com.ticketland.ticketland.user.controller;

import com.ticketland.ticketland.user.dto.EmailDto;
import com.ticketland.ticketland.user.dto.EmailVerifyDto;
import com.ticketland.ticketland.user.dto.JoinDto;
import com.ticketland.ticketland.user.service.UserEmailService;
import com.ticketland.ticketland.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserEmailService userEmailService;

    @PostMapping("/email-verification/request")
    public ResponseEntity<?> sendVerificationCode(@RequestBody @Valid EmailDto emailDto) {
        userEmailService.sendVerificationCode(emailDto.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email-verification/confirm")
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid EmailVerifyDto emailVerifyDTO) {
        userEmailService.verifyEmail(emailVerifyDTO.getEmail(), emailVerifyDTO.getVerifyCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto.Request requestDto) {
        return ResponseEntity.ok(userService.join(requestDto));
    }

}
