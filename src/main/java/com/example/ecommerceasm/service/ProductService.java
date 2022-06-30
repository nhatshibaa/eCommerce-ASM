package com.example.ecommerceasm.service;

import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<Product> pageTu = productRepository.findAll(pageable);
        List<Product> list = pageTu.getContent();
        responses.put("content", list); // chi tiết các phần tử được
        responses.put("currentPage", pageTu.getNumber() + 1);// trang hiện tại
        responses.put("totalItems", pageTu.getTotalElements());// tổng số phàn tử
        responses.put("totalPage", pageTu.getTotalPages()); // tổng só trang
        return responses;
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
