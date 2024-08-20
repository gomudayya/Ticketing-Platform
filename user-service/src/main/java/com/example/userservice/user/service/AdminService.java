package com.example.userservice.user.service;

import com.example.servicecommon.auth.UserRole;
import com.example.userservice.global.util.AesUtil;
import com.example.userservice.user.domain.User;
import com.example.userservice.user.dto.AdminJoinRequest;
import com.example.userservice.user.dto.UserInfoResponse;
import com.example.userservice.user.exception.AdminKeyUnmatchedException;
import com.example.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    @Value("${admin-secret-key}")
    private String adminKey;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserInfoResponse adminJoin(AdminJoinRequest adminJoinRequest) {
        if (!adminJoinRequest.getAdminKey().equals(adminKey)) {
            throw new AdminKeyUnmatchedException();
        }

        User user = User.builder()
                .email(AesUtil.encode(adminJoinRequest.getEmail()))
                .password(passwordEncoder.encode(adminJoinRequest.getPassword()))
                .name(AesUtil.encode(adminJoinRequest.getName()))
                .phoneNumber(AesUtil.encode(adminJoinRequest.getPhoneNumber()))
                .userRole(UserRole.ADMIN)
                .build();

        userRepository.save(user);
        return UserInfoResponse.from(user);
    }
}
