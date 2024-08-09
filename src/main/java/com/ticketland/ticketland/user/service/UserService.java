package com.ticketland.ticketland.user.service;

import com.ticketland.ticketland.user.domain.JoinVerify;
import com.ticketland.ticketland.user.domain.User;
import com.ticketland.ticketland.user.dto.JoinRequest;
import com.ticketland.ticketland.user.dto.UserInfoResponse;
import com.ticketland.ticketland.user.exception.EmailNotVerifiedException;
import com.ticketland.ticketland.user.exception.VerifyExpiredException;
import com.ticketland.ticketland.user.repository.JoinVerifyRepository;
import com.ticketland.ticketland.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JoinVerifyRepository joinVerifyRepository;
    private final PasswordEncoder passwordEncoder;
    private final AesBytesEncryptor aesBytesEncryptor;

    @Transactional
    public UserInfoResponse join(JoinRequest joinRequest) {
        JoinVerify joinVerify = joinVerifyRepository.findById(joinRequest.getEmail())
                .orElseThrow(VerifyExpiredException::new);

        if (!joinVerify.isVerified()) {
            throw new EmailNotVerifiedException(); // 이메일 인증이 되어있지 않으면 예외 발생
        }

        User user = joinRequest.toEntity();
        UserInfoResponse response = UserInfoResponse.from(user);

        user.encryptUser(passwordEncoder, aesBytesEncryptor);
        userRepository.save(user);
        return response;
    }
}
