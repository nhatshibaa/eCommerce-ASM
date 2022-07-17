package com.example.ecommerceasm.seeder;

import com.example.ecommerceasm.entity.User;
import com.example.ecommerceasm.enums.UserStatus;
import com.example.ecommerceasm.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeeder {
    public static List<User> users;
    public static final int NUMBER_OF_USER = 50;

    @Autowired
    UserRepository userRepository;

    public void generate() {
        Faker faker = new Faker();
        users = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_USER; i++) {
            users.add(User.builder()
                            .fullname(faker.name().fullName())
                            .phone(faker.phoneNumber().cellPhone())
                            .email(faker.name().username() + "@gmail.com")
                            .address(faker.address().streetAddress())
                            .city(faker.address().cityName())
                            .district(faker.address().streetName())
                            .ward(faker.address().state())
                            .status(UserStatus.ACTIVE)
                            .build());
        }
        userRepository.saveAll(users);
    }
}
