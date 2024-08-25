package com.example.orderservice.payment.controller;

import com.example.orderservice.payment.dto.PaymentRequest;
import com.example.orderservice.payment.service.PaymentService;
import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.dto.UserClaim;
import com.example.servicecommon.resolver.AuthPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/success")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<?> paymentSuccess(@AuthPrincipal UserClaim userClaim, @RequestBody @Valid PaymentRequest paymentRequest) {
        paymentService.successPayment(userClaim.getUserId(), paymentRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/fail")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<?> paymentFail(@AuthPrincipal UserClaim userClaim, @RequestBody @Valid PaymentRequest paymentRequest) {
        paymentService.failPayment(userClaim.getUserId(), paymentRequest);
        return ResponseEntity.noContent().build();
    }
}
