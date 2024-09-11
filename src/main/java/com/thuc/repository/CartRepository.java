package com.thuc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thuc.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    public Cart findByCustomerId(Long userId);
}
