package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.repository.ProductRepository;
import com.example.ecommerceasm.specification.ProductSpecification;
import com.example.ecommerceasm.specification.OrderSearchBody;
import com.example.ecommerceasm.specification.SearchCriteria;
import com.example.ecommerceasm.specification.SearchCriteriaOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Product> pageTotal = productRepository.findAll(pageable);
        List<Product> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }



    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
