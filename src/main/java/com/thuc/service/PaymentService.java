package com.thuc.service;

import com.thuc.model.Order;
import com.thuc.response.PaymentResponse;

public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order) throws Exception;

}
