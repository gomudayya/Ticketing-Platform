package com.ticketland.ticketland.user.service;

import com.ticketland.ticketland.user.domain.User;
import com.ticketland.ticketland.user.dto.JoinDto;
import com.ticketland.ticketland.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public JoinDto.Response join(JoinDto.Request requestDto) {

        return null;
    }
}
