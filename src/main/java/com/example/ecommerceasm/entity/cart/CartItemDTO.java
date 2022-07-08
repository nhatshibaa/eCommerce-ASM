package com.example.ecommerceasm.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Integer productId;
    private Integer unitPrice;
    private Integer quantity;
}
