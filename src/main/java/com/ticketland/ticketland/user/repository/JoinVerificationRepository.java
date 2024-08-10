package com.ticketland.ticketland.user.repository;

import com.ticketland.ticketland.user.domain.JoinVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinVerificationRepository extends CrudRepository<JoinVerification, String> {
}
