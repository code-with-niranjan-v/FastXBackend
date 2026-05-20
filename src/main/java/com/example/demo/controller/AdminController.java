package com.example.demo.controller;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Booking;
import com.example.demo.model.Bus;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepo;
import com.example.demo.repository.BusRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.AdminService;
import com.example.demo.service.UserService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private AdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>>
    getAllUsers() {

        List<User> users =
                userRepo.findAll();

        List<UserDTO> userDTOS =
                UserMapper
                        .toListOfUserDTO(
                                users
                        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Users fetched",

                        userDTOS
                )
        );
    }

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<?>>
    getAllBookings() {

        List<Booking> bookings =
                bookingRepo.findAll();

        List<BookingDTO> bookingDTOS =
                BookingMapper
                        .toListOfBookingDTO(
                                bookings
                        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Bookings fetched",

                        bookingDTOS
                )
        );
    }

    @GetMapping("/buses")
    public ResponseEntity<ApiResponse<?>>
    getAllBuses() {

        List<Bus> buses =
                busRepo.findAll();

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Buses fetched",

                        buses
                )
        );
    }

    @PreAuthorize(
            "hasRole('ADMIN')"
    )

    @DeleteMapping(
            "/delete-user/{id}"
    )
    public ResponseEntity<ApiResponse<?>>
    deleteUser(
            @PathVariable
            int id
    ) throws ResourceNotFoundException {

        adminService.deleteUser(
                id
        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "User deleted successfully",

                        null
                )
        );
    }
}