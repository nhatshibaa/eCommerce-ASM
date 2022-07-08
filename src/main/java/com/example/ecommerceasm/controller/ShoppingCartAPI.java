package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.entity.OrderDetail;
import com.example.ecommerceasm.entity.OrderDetailId;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.entity.cart.CartItemDTO;
import com.example.ecommerceasm.entity.cart.ShoppingCartDTO;
import com.example.ecommerceasm.enums.OrderStatus;
import com.example.ecommerceasm.repository.ProductRepository;
import com.example.ecommerceasm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@CrossOrigin(origins = "*")
public class ShoppingCartAPI {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST, path = "add")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        try {
            for (CartItemDTO cartItemDTO :
                    shoppingCartDTO.getItems()) {
                shoppingCartDTO.addTotalPrice(cartItemDTO);
            }

            Order order = new Order();
            order.setUserId(shoppingCartDTO.getUserId());
            order.setTotalPrice(shoppingCartDTO.getTotalPrice());
            order.setStatus(OrderStatus.PENDING);

            Order orderSave = orderService.saveCart(order);
            Set<OrderDetail> orderDetails = new HashSet<>();
            for (CartItemDTO cartItemDTO : shoppingCartDTO.getItems()) {
                OrderDetail orderDetail = new OrderDetail();
                OrderDetailId orderDetailId = new OrderDetailId();
                orderDetailId.setOrderId(orderSave.getId());
                orderDetailId.setProductId(cartItemDTO.getProductId());

                orderDetail.setId(orderDetailId);

                Product product = productRepository.findById(cartItemDTO.getProductId()).orElse(null);
                if (product == null) {
                    return new ResponseEntity("Product not found", HttpStatus.BAD_REQUEST);
                }
                orderDetail.setOrder(orderSave);
                orderDetail.setProduct(product);

                orderDetail.setQuantity(cartItemDTO.getQuantity());
                orderDetail.setUnitPrice(cartItemDTO.getUnitPrice());
                orderDetails.add(orderDetail);
            }

            order.setOrderDetails(orderDetails);
            Order orderCheckSave = orderService.saveCart(order);
            return ResponseEntity.ok(orderService.saveCart(orderCheckSave));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
