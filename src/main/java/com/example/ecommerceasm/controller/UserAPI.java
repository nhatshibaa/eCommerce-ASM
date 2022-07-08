package com.example.ecommerceasm.controller;

import com.example.ecommerceasm.entity.Product;
import com.example.ecommerceasm.entity.User;
import com.example.ecommerceasm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "*")
public class UserAPI {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        try {
            return ResponseEntity.ok(userService.findAll(PageRequest.of(page - 1, limit)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> getDetail(@PathVariable int id) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
