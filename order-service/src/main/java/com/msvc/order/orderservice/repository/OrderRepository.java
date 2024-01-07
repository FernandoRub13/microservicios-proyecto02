package com.msvc.order.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msvc.order.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
