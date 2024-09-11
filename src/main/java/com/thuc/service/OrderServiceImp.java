package com.thuc.service;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thuc.model.Address;
import com.thuc.model.Cart;
import com.thuc.model.CartItem;
import com.thuc.model.Order;
import com.thuc.model.OrderItem;
import com.thuc.model.Restaurant;
import com.thuc.model.User;
import com.thuc.repository.AddressRepository;
import com.thuc.repository.OrderItemRepository;
import com.thuc.repository.OrderRepository;
import com.thuc.repository.UserRepository;
import com.thuc.request.OrderRequest;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shipAddress = order.getDeliveryAddress();

        Address saveAddress = addressRepository.save(shipAddress);

        if (!user.getAddresses().contains(saveAddress)) {
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }

        Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());

        Order newOrder = new Order();
        newOrder.setCustomer(user);
        newOrder.setCreatedAt(new Date());
        newOrder.setOrderstatus("PENDING");
        newOrder.setDeliveryAddress(saveAddress);
        newOrder.setRestaurant(restaurant);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setIngredients(item.getIngredients());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);

        }

        Long totalPrice = cartService.caculateCartTotals(cart);

        newOrder.setItems(orderItems);
        newOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(newOrder);

        restaurant.getOrders().add(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order = findOrderById(orderId);
        if (orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")) {
            order.setOrderstatus(orderStatus);
            return orderRepository.save(order);
        }

        throw new Exception("please select a valid order status");
    }

    @Override
    public void calcelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderstatus().equals(orderStatus))
                    .collect(Collectors.toList());
        }

        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            throw new Exception("Order not found");
        }

        return orderOptional.get();

    }

}
