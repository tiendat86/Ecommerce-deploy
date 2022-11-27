package com.ecom.repository;

import com.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameOrEmail(String username, String email);
    User findByVerifyCode(String verifyCode);
}
