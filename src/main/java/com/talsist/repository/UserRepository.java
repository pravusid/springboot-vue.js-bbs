package com.talsist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talsist.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(String userId);

}
