package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public void deleteUser(
            int id
    ) throws ResourceNotFoundException {

        User user =
                userRepo.findById(id)
                        .orElseThrow(

                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        user.setActive(false);

        userRepo.save(user);
    }
}
