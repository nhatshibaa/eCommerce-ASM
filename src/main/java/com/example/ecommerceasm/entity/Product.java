package com.example.ecommerceasm.entity;

import com.example.ecommerceasm.enums.ProductStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    private String id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String slug;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String detail;
    private BigDecimal price;
    private int quantity;
    @Column(columnDefinition = "TEXT")
    private String thumbnails;
    private ProductStatus status;

    @ManyToOne
    private Category cate;

}
