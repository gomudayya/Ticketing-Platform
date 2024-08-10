package com.ticketland.ticketland.auth.dto;

import com.ticketland.ticketland.user.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtClaimDto {
    private Long id;
    private UserRole userRole;
}
