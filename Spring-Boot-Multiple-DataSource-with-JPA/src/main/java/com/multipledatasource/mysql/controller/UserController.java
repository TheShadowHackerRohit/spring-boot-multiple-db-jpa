package com.multipledatasource.mysql.controller;

import com.multipledatasource.mysql.entities.User;
import com.multipledatasource.mysql.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepo userRepo;

    @PostMapping("/save-user")
    public ResponseEntity addUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email){

        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        User savedUser = userRepo.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @GetMapping("/get-by-id")
    public ResponseEntity getByID(@RequestParam int id){
        User user = userRepo.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }

    @GetMapping("/find-all-user")
    public ResponseEntity getAllUser(){
        List<User> userList = userRepo.findAll();
        return new ResponseEntity<>(userList, HttpStatus.FOUND);
    }

    @PutMapping("/update-email/{id}")
    public ResponseEntity updateEmailById(@PathVariable int id, @RequestParam String newEmail){
        User user = userRepo.findById(id).get();
        user.setEmail(newEmail);

        User savedUser =  userRepo.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userRepo.deleteById(id);
        return "User has been deleted";

    }

}
