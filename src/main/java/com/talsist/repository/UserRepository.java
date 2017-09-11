package com.talsist.repository;

import com.talsist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(String userId);

}
