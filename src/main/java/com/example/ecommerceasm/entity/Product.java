package com.example.ecommerceasm.entity;

import com.example.ecommerceasm.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Builder
@Table(name = "products")
public class Product extends BaseEntity{
    @Id
    private String id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String detail;
    private double price;
    private int quantity;
    @Column(columnDefinition = "TEXT")
    private String thumbnails;
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "cate_id", referencedColumnName = "id", nullable = false)
    private Category cateId;
}