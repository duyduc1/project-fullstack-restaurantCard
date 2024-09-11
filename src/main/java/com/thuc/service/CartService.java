package com.thuc.service;

import com.thuc.model.Cart;
import com.thuc.model.CartItem;
import com.thuc.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCard(AddCartItemRequest req, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception;

    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception;

    public Long caculateCartTotals(Cart cart) throws Exception;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId) throws Exception;

}
