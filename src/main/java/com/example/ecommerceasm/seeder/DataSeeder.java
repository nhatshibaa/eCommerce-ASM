package com.example.ecommerceasm.seeder;

import com.example.ecommerceasm.entity.Category;
import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.enums.ProductStatus;
import com.example.ecommerceasm.repository.CateRepository;
import com.example.ecommerceasm.repository.ProductRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    boolean createSeedData = false;
    final ProductRepository productRepository;
    final CateRepository cateRepository;
    Faker faker;
    Random random = new Random();

    int numberOfProduct = 500;

    public DataSeeder(ProductRepository productRepository, CateRepository cateRepository) {
        this.productRepository = productRepository;
        this.cateRepository = cateRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {
        if(createSeedData){
            productRepository.deleteAll();
            cateRepository.deleteAll();
            seedCate();
            seedProduct();
        }
    }

    private void seedCate() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Cỏ"));
        categories.add(new Category(2, "Cây"));
        categories.add(new Category(3, "Hoa"));
        categories.add(new Category(4, "Lá"));
        categories.add(new Category(5, "Cành"));
        cateRepository.saveAll(categories);
    }

    private void seedProduct() {
        List<Product> listProduct = new ArrayList<>();
        List<Category> categories = cateRepository.findAll();
        for (int i = 0; i < numberOfProduct; i++) {
            Product product = new Product();
            product.setName(faker.funnyName().name());
            product.setDescription(faker.lorem().paragraph());
            product.setQuantity(faker.number().numberBetween(10, 100));
            product.setPrice(faker.number().numberBetween(50, 200) * 1000);
            product.setCategory(categories.get(random.nextInt(categories.size())));
            product.setDetail(faker.lorem().paragraph());
            product.setThumbnails(faker.avatar().image());
            product.setStatus(ProductStatus.AVAILABLE);
            product.setCreatedAt(LocalDateTime.now());
            listProduct.add(product);
        }
        productRepository.saveAll(listProduct);
    }
}
