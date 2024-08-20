package com.example.userservice.auth.dto;

import com.example.servicecommon.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtClaimDto {
    private Long id;
    private UserRole userRole;
}
