package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Order;
import com.example.ecommerceasm.entity.OrderDetail;
import com.example.ecommerceasm.entity.OrderDetailId;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.entity.cart.CartItem;
import com.example.ecommerceasm.entity.cart.ShoppingCart;
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
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class OrderApi {
    @Autowired
    OrderService orderService;

    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST, path = "shopping-cart")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCart shoppingCart){
        for (CartItem cartItem :
                shoppingCart.getItems()) {
            shoppingCart.addTotalPrice(cartItem);
        }

        Order order = new Order();
        order.setUserId(shoppingCart.getUserId());
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);

        Order orderSave = orderService.saveCart(order);
        Set<OrderDetail> orderDetails = new HashSet<>();
        for (CartItem cartItem : shoppingCart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(orderSave.getId());
            orderDetailId.setProductId(cartItem.getProductId());

            orderDetail.setId(orderDetailId);

            Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
            if (product == null){
                return new ResponseEntity("Co loi", HttpStatus.NOT_FOUND);
            }
            orderDetail.setOrder(orderSave);
            orderDetail.setProduct(product);

            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getUnitPrice());
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        Order orderCheckSave = orderService.saveCart(order);
        return ResponseEntity.ok(orderService.saveCart(orderCheckSave));
    }
}
