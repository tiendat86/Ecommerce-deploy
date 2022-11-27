package com.ecom.repository;

import com.ecom.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAllById(Long addressId);
}
