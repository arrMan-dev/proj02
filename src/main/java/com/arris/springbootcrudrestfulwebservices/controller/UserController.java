package com.arris.springbootcrudrestfulwebservices.controller;

import com.arris.springbootcrudrestfulwebservices.entity.User;
import com.arris.springbootcrudrestfulwebservices.exception.ResourceNotFoundException;
import com.arris.springbootcrudrestfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    //get all users
    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable (value = "id") long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + userId + "is not found."));
    }

    //create user
    @PostMapping
    public User createUser(@RequestBody User user){
        return this.userRepository.save(user);
    }

    //update user
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable(value = "id") long userId){
        User existing = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + userId + "is not found."));
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        return this.userRepository.save(existing);
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") long userId){
        User existingUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id:" + userId + "is not found."));
        this.userRepository.delete(existingUser);
        return ResponseEntity.ok().build();
    }
}
