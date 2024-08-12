package com.example.ecommerceapi.feature.user;

import com.example.ecommerceapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void disableUser(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = false WHERE u.id = :id")
    void enableUser(@Param("id") Long id);

}
