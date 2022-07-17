package com.example.ecommerceasm.seeder;

import com.example.ecommerceasm.entity.Category;
import com.example.ecommerceasm.repository.CateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationSeeder implements CommandLineRunner {

    boolean createSeedData = false;
    final CateRepository cateRepository;

    @Autowired
    OrderSeeder orderSeeder;
    @Autowired
    ProductSeeder productSeeder;
    @Autowired
    UserSeeder userSeeder;

    public ApplicationSeeder(CateRepository cateRepository) {
        this.cateRepository = cateRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (createSeedData) {
            seedCate();
            productSeeder.generate();
            userSeeder.generate();
            orderSeeder.generate();

        }
    }

    private void seedCate() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Cỏ"));
        categories.add(new Category("Cây"));
        categories.add(new Category("Hoa"));
        categories.add(new Category("Lá"));
        categories.add(new Category("Cành"));
        cateRepository.saveAll(categories);
    }

}
