package com.ecom.repository;

import com.ecom.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findBillById(Long id);
}
