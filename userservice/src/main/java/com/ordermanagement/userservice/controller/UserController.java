package com.ordermanagement.userservice.controller;

import com.ordermanagement.userservice.dto.UserRequest;
import com.ordermanagement.userservice.entity.User;
import com.ordermanagement.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ordermanagement")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createuser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{'status': '0', 'message': 'user registered successfully'}", HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable long id) {
        Optional<User> user;
        try {
            user = userRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            String username = userRequest.getUsername();
            Optional<User> user = userRepository.findByUsername(username);
            user.ifPresentOrElse((u -> {
                u.setEmail(userRequest.getEmail());
                u.setPassword(userRequest.getPassword());
                userRepository.save(u);
            }), () -> {
                throw new RuntimeException("User Does Not Exist");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{ 'status': '0', 'message': 'user updated successfully' }", HttpStatus.OK);
    }

    @PostMapping("deleteuser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        Optional<User> user;
        try {
            user = userRepository.findByUsername(username);
            user.ifPresentOrElse(u -> {
                userRepository.delete(u);
            }, () -> {
                throw new RuntimeException("user does not exist");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{ 'status' : '0', 'message' : 'user deleted successfully' }", HttpStatus.OK);
    }
}
