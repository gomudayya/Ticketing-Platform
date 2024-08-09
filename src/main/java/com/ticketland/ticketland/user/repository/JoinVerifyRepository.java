package com.ticketland.ticketland.user.repository;

import com.ticketland.ticketland.user.domain.JoinVerify;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoinVerifyRepository extends CrudRepository<JoinVerify, String> {
}
