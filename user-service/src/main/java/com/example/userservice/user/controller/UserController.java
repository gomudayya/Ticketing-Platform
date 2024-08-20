package com.example.userservice.user.controller;

import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.dto.UserClaim;
import com.example.servicecommon.resolver.AuthPrincipal;
import com.example.userservice.user.dto.UserInfoResponse;
import com.example.userservice.user.service.AdminService;
import com.example.userservice.user.service.UserEmailService;
import com.example.userservice.user.service.UserService;
import com.example.userservice.user.dto.AdminJoinRequest;
import com.example.userservice.user.dto.EmailRequest;
import com.example.userservice.user.dto.EmailVerifyRequest;
import com.example.userservice.user.dto.JoinRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final AdminService adminService;

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
    public ResponseEntity<UserInfoResponse> join(@RequestBody @Valid JoinRequest requestDto) {
        return ResponseEntity.ok(userService.join(requestDto));
    }

    @GetMapping
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<UserInfoResponse> getUserByAccessToken(@AuthPrincipal UserClaim userClaim) {
        return ResponseEntity.ok(userService.findUserInfo(userClaim.getUserId()));
    }

    @PostMapping("/adminJoin")
    public ResponseEntity<UserInfoResponse> adminJoin(@RequestBody @Valid AdminJoinRequest adminJoinRequest) {
        return ResponseEntity.ok(adminService.adminJoin(adminJoinRequest));
    }
}
