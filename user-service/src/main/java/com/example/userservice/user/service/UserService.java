package com.example.userservice.user.service;

import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.exception.NotFoundException;
import com.example.userservice.global.util.AesUtil;
import com.example.userservice.user.domain.JoinVerification;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.dto.UserInfoResponse;
import com.example.userservice.user.dto.JoinRequest;
import com.example.userservice.user.exception.DuplicatedEmailException;
import com.example.userservice.user.exception.EmailNotVerifiedException;
import com.example.userservice.user.exception.VerifyExpiredException;
import com.example.userservice.user.repository.JoinVerificationRepository;
import com.example.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JoinVerificationRepository joinVerificationRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse join(JoinRequest joinRequest) {
        JoinVerification joinVerification = joinVerificationRepository.findById(joinRequest.getEmail())
                .orElseThrow(VerifyExpiredException::new);

        if (!joinVerification.isVerified()) {
            throw new EmailNotVerifiedException(); // 이메일 인증이 되어있지 않으면 예외 발생
        }

        String encodedEmail = AesUtil.encode(joinRequest.getEmail());
        if (userRepository.existsByEmail(encodedEmail)) {
            throw new DuplicatedEmailException();
        }

        User user = User.builder()
                .email(encodedEmail)
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .name(AesUtil.encode(joinRequest.getName()))
                .phoneNumber(AesUtil.encode(joinRequest.getPhoneNumber()))
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);
        return UserInfoResponse.from(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse findUserInfo(Long userId) {
        User user = findUserById(userId);
        return UserInfoResponse.from(user);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저"));
    }
}
