package com.talsist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talsist.domain.PersistentLogins;

public interface PersistentLoginsRepository extends JpaRepository<PersistentLogins, String> {

    public void deleteByUserId(Long userId);

}
