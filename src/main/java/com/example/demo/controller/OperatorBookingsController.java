package com.example.demo.controller;

import com.example.demo.mapper.BookingMapper;
import com.example.demo.model.User;
import com.example.demo.response.ApiResponse;
import com.example.demo.security.service.UserPrincipal;
import com.example.demo.service.BookingService;
import com.example.demo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operator/bookings")
@PreAuthorize("hasRole('OPERATOR')||hasRole('ADMIN')")
public class OperatorBookingsController {
    @Autowired
    private BookingService bookingService;
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getMyBookings(Authentication authentication) {
        System.out.println(authentication.getName());
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Bookings fetched",
                        bookingService.getAllBookings(user))
        );
    }

}
