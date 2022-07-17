package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.service.OrderService;
import com.example.ecommerceasm.specification.OrderSearchBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderAPI {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "orderId", required = false) String orderId,
            @RequestParam(name = "cateId", required = false) String cateId,
            @RequestParam(name = "nameUser", required = false) String nameUser,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "nameProduct", required = false) String nameProduct,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end
    ) {
        OrderSearchBody searchBody = OrderSearchBody.OrderSearchBodyBuilder.anOrderSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withOrderId(orderId)
                .withCateId(cateId)
                .withPhone(phone)
                .withNameUser(nameUser)
                .withNameProduct(nameProduct)
                .withNameProduct(email)
                .withSort(sort)
                .withStart(start)
                .withEnd(end)
                .build();

        return ResponseEntity.ok(orderService.findOrdersBy(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    
}