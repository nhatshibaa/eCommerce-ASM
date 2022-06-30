package com.example.ecommerceasm.repository;

import com.example.ecommerceasm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CateRepository extends JpaRepository<Category, Integer> {
}
