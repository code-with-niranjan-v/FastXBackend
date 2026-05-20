package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void deleteUser(int id) throws ResourceNotFoundException;
}
