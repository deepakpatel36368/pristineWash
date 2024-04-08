package com.website.pristinewash.controller;

import com.website.pristinewash.entity.User;
import com.website.pristinewash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{user_id}")
    ResponseEntity<?> getUserById(@PathVariable Integer user_id) {
       Optional<User>  user = userRepository.findById(user_id);
       if(user.isPresent()) {
           return new ResponseEntity<>(user.get(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
       }
    }
    //any first service
}
