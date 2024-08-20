package com.example.apigateway.dto;


import com.example.apigateway.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtClaimDto {
    private Long id;
    private UserRole userRole;
}
