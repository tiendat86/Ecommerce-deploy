package com.ecom.repository;

import com.ecom.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<PhoneNumber, Long> {
    PhoneNumber findAllById(Long phoneNumberId);
    PhoneNumber findByPhoneAndUser_Id(String phone, Long userId);
}
