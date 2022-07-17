package com.example.ecommerceasm.seeder;

import com.example.ecommerceasm.entity.Category;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.enums.ProductStatus;
import com.example.ecommerceasm.repository.CateRepository;
import com.example.ecommerceasm.repository.ProductRepository;
import com.example.ecommerceasm.util.StringHelper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class ProductSeeder {

    public static List<Product> products;
    public static final int NUMBER_OF_PRODUCT = 500;
    Random random = new Random();
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CateRepository cateRepository;

    public void generate() {
        Faker faker = new Faker();
        products = new ArrayList<>();
        Product product = new Product();
        List<Category> categories = cateRepository.findAll();
        for (int i = 0; i < NUMBER_OF_PRODUCT; i++) {
            products.add(Product.builder()
                    .name(faker.name().name())
//                    .slug(StringHelper.toSlug(product.getName()))
                    .price(BigDecimal.valueOf(faker.number().numberBetween(50, 200) * 1000))
                    .quantity(faker.number().numberBetween(10, 100))
                    .thumbnails(faker.avatar().image())
                    .cate(categories.get(random.nextInt(categories.size())))
                    .detail(faker.lorem().paragraph())
                    .description(faker.lorem().sentence())
                    .status(ProductStatus.AVAILABLE)
                    .build());
        }
        productRepository.saveAll(products);
    }
}
