package com.example.ecommerceasm.entity.cart;

import com.example.ecommerceasm.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    private String userId;
//    private String shipName;
//    private String shipPhone;
//    private String shipAddress;
//    private String shipNote;
    private Integer totalPrice;
    private Set<CartItem> items;

    public void addTotalPrice(CartItem cartItem) {
        if(this.totalPrice == null){
            this.totalPrice = 0;
        }
        this.totalPrice += (cartItem.getUnitPrice() * cartItem.getQuantity());
    }
}
