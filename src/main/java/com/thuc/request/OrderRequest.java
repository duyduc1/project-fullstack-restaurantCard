package com.thuc.request;

import com.thuc.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;
}
