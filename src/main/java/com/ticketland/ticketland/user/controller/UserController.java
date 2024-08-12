package com.ticketland.ticketland.user.controller;

import com.ticketland.ticketland.user.constant.UserRole;
import com.ticketland.ticketland.user.dto.EmailRequest;
import com.ticketland.ticketland.user.dto.EmailVerifyRequest;
import com.ticketland.ticketland.user.dto.JoinRequest;
import com.ticketland.ticketland.user.dto.UserInfoResponse;
import com.ticketland.ticketland.user.service.UserEmailService;
import com.ticketland.ticketland.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<UserInfoResponse> join(@RequestBody JoinRequest requestDto) {
        return ResponseEntity.ok(userService.join(requestDto));
    }

    @GetMapping
    @Secured(UserRole.Authority.USER)
    public ResponseEntity<UserInfoResponse> getUserByAccessToken(@AuthenticationPrincipal Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
