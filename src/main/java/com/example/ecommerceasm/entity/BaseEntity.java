package com.example.ecommerceasm.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @CreatedDate
    public LocalDateTime createdAt;
    @LastModifiedDate
    public LocalDateTime updatedAt;
    public LocalDateTime deletedAt;
    public String createdBy;
    public String updatedBy;
    public String deletedBy;
}
