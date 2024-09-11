package com.thuc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thuc.model.CartItem;
import com.thuc.model.Order;
import com.thuc.model.User;
import com.thuc.request.AddCartItemRequest;
import com.thuc.request.OrderRequest;
import com.thuc.response.PaymentResponse;
import com.thuc.service.OrderService;
import com.thuc.service.PaymentService;
import com.thuc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody OrderRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.createOrder(req, user);
        PaymentResponse res = paymentService.createPaymentLink(order);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Order> order = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
