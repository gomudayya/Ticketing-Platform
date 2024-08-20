package com.example.servicecommon.dto;

import com.example.servicecommon.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserClaim {
    private Long userId;
    private UserRole role;
}
