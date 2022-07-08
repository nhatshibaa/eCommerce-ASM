package com.example.ecommerceasm.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private String userId;
//    private String shipName;
//    private String shipPhone;
//    private String shipAddress;
//    private String shipNote;
    private Integer totalPrice;
    private Set<CartItemDTO> items;

    public void addTotalPrice(CartItemDTO cartItemDTO) {
        if(this.totalPrice == null){
            this.totalPrice = 0;
        }
        this.totalPrice += (cartItemDTO.getUnitPrice() * cartItemDTO.getQuantity());
    }
}
