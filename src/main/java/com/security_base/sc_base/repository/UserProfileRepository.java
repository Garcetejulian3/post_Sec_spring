package com.security_base.sc_base.repository;

import com.security_base.sc_base.models.UserProfile;
import com.security_base.sc_base.models.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    Optional<UserProfile> findByUserSec(UserSec userSec);

}
