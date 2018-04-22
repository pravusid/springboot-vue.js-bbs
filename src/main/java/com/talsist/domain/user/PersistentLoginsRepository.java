package com.talsist.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistentLoginsRepository extends JpaRepository<PersistentLogins, String> {

    public void deleteByUserId(Long userId);

}
