package com.example.ecommerceasm.entity.cart;

import com.example.ecommerceasm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    private User userId;
    private String shipName;
    private String shipPhone;
    private String shipAddress;
    private String shipNote;
    private BigDecimal totalPrice;
    private Set<CartItemDTO> items;

    public void addTotalPrice(CartItemDTO cartItemDTO) {
        if(this.totalPrice == null){
            this.totalPrice = BigDecimal.valueOf(0);
        }
        BigDecimal quantityInBigDecimal = new BigDecimal(cartItemDTO.getQuantity());
        this.totalPrice = this.totalPrice.add(cartItemDTO.getUnitPrice()).multiply(quantityInBigDecimal);
    }
}
