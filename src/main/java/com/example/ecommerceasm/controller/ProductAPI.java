package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@Log4j2
public class ProductAPI {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAllBy(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        try {
            return ResponseEntity.ok(productService.findAll(PageRequest.of(page - 1, limit)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> getDetail(@PathVariable int id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<Product> update(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();
        // map object
        existProduct.setName(product.getName());
        existProduct.setCategory(product.getCategory());
        existProduct.setPrice(product.getPrice());
        existProduct.setDescription(product.getDescription());
        existProduct.setDetail(product.getDetail());
        return ResponseEntity.ok(productService.save(existProduct));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public boolean delete(@PathVariable String id) {

        return true;
    }
}
