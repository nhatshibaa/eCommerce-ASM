package com.example.ecommerceasm.entity.cart;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String slug;
    private Integer quantity;
    private String description;
    private String detail;
    private String thumbnails;
    private String createdAt;
    private String updatedAt;
    private double price;
    private String status;
}
