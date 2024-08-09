package com.ticketland.ticketland.user.controller;

import com.ticketland.ticketland.user.dto.EmailRequest;
import com.ticketland.ticketland.user.dto.EmailVerifyRequest;
import com.ticketland.ticketland.user.dto.JoinRequest;
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
    public ResponseEntity<?> sendVerificationCode(@RequestBody @Valid EmailRequest emailRequest) {
        userEmailService.sendVerificationCode(emailRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email-verification/confirm")
    public ResponseEntity<?> verifyEmail(@RequestBody @Valid EmailVerifyRequest emailVerifyRequest) {
        userEmailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getVerifyCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinRequest requestDto) {
        return ResponseEntity.ok(userService.join(requestDto));
    }
}
