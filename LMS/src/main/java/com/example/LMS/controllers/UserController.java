package com.example.LMS.controllers;

import com.example.LMS.models.AuthentictationResponse;
import com.example.LMS.models.Profile;
import com.example.LMS.models.User;
import com.example.LMS.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<AuthentictationResponse> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user)); // Register method in service
    }

    @PostMapping("/login")
    public ResponseEntity<AuthentictationResponse> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user)); // Login method in service
    }


    @PostMapping("/editprofile")
    public ResponseEntity<Object> editProfile(@RequestBody Profile userProfile){
        return ResponseEntity.ok(userService.editProfile(userProfile));
    }


    @GetMapping("/profiles/{userId}")
    public ResponseEntity<Object> ViewProfile(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.ViewProfile(userId));

        /// /
    }
}
