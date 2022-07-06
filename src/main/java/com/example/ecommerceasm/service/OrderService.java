package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Optional<?> findById (String id){
        return orderRepository.findById(id);
    }

    public Order saveCart(Order order){
        return orderRepository.save(order);
    }
}
