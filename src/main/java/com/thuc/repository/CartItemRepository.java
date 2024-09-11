package com.thuc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuc.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
