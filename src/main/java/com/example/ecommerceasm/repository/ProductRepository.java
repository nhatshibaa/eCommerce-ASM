package com.example.ecommerceasm.repository;

import com.example.ecommerceasm.entity.Product;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product> {
    List<Product> findByName(String name);
    Page<Product> findByNameContaining(Pageable pageable, String name);
}

