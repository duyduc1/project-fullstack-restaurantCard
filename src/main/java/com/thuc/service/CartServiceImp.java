package com.thuc.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thuc.model.Cart;
import com.thuc.model.CartItem;
import com.thuc.model.Food;
import com.thuc.model.User;
import com.thuc.repository.CartItemRepository;
import com.thuc.repository.CartRepository;
import com.thuc.repository.FoodRepository;
import com.thuc.request.AddCartItemRequest;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCard(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem item : cart.getItems()) {
            if (item.getFood().equals(food)) {
                int newQuantity = item.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(item.getId(), newQuantity);

            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(food.getPrice() * req.getQuantity());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem cartItem = cartItemOptional.get();
        cartItem.setQuantity(quantity);

        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }

        CartItem cartItem = cartItemOptional.get();

        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }

    @Override
    public Long caculateCartTotals(Cart cart) throws Exception {

        Long total = 0L;

        for (CartItem item : cart.getItems()) {
            total += item.getFood().getPrice() * item.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> cartOptional = cartRepository.findById(id);

        if (cartOptional.isEmpty()) {
            throw new Exception("Cart not found with id: " + id);
        }

        return cartOptional.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        // User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(caculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        // User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userId);

        cart.getItems().clear();

        return cartRepository.save(cart);
    }

}
