package com.ecom.eilco.repository;


import com.ecom.eilco.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}