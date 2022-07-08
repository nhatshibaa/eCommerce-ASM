package com.example.ecommerceasm.entity.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer price;
    private Integer status;
}
