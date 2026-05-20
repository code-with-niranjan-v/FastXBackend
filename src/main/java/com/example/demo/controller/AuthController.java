package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody RegisterDTO registerDTO) throws RoleNotFoundException {
        return authService.registerUser(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDTO loginDTO){
        return authService.login(loginDTO);
    }

}
