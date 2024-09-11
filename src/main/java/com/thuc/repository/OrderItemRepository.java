package com.thuc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuc.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
