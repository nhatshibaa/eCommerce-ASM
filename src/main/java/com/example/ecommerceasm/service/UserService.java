package com.example.ecommerceasm.service;


import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.entity.User;
import com.example.ecommerceasm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Map<String, Object> findAll(Pageable pageable) {
        Map<String, Object> responses = new HashMap<>();
        Page<User> pageTotal = userRepository.findAll(pageable);
        List<User> list = pageTotal.getContent();
        responses.put("content", list);
        responses.put("currentPage", pageTotal.getNumber() + 1);
        responses.put("totalItems", pageTotal.getTotalElements());
        responses.put("totalPage", pageTotal.getTotalPages());
        return responses;
    }
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
