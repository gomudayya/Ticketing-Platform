package com.ticketland.ticketland.user.service;

import com.ticketland.ticketland.global.util.AesUtil;
import com.ticketland.ticketland.user.constant.UserRole;
import com.ticketland.ticketland.user.domain.JoinVerification;
import com.ticketland.ticketland.user.domain.User;
import com.ticketland.ticketland.user.dto.JoinRequest;
import com.ticketland.ticketland.user.dto.UserInfoResponse;
import com.ticketland.ticketland.user.exception.EmailNotVerifiedException;
import com.ticketland.ticketland.user.exception.UserNotFoundException;
import com.ticketland.ticketland.user.exception.VerifyExpiredException;
import com.ticketland.ticketland.user.repository.JoinVerificationRepository;
import com.ticketland.ticketland.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JoinVerificationRepository joinVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserInfoResponse join(JoinRequest joinRequest) {
        JoinVerification joinVerification = joinVerificationRepository.findById(joinRequest.getEmail())
                .orElseThrow(VerifyExpiredException::new);

        if (!joinVerification.isVerified()) {
            throw new EmailNotVerifiedException(); // 이메일 인증이 되어있지 않으면 예외 발생
        }

        User user = User.builder()
                .email(AesUtil.encode(joinRequest.getEmail()))
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .name(AesUtil.encode(joinRequest.getName()))
                .phoneNumber(AesUtil.encode(joinRequest.getPhoneNumber()))
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);
        return UserInfoResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return UserInfoResponse.from(user);
    }
}
