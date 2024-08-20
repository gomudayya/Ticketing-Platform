package com.example.userservice.user.repository;

import com.example.userservice.user.domain.JoinVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinVerificationRepository extends CrudRepository<JoinVerification, String> {
}
