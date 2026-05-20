package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.InsufficientWalletException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SeatAlreadyBookedException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Booking;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<?>> getProfile(Authentication authentication) {

        String email = authentication.getName(); // from JWT

        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Profile fetched", UserMapper.toUserDTO(user))
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<?>>
    updateProfile(

            Authentication authentication,

            @RequestBody
            ProfileDTO profileDTO
    ) {

        String email =
                authentication.getName();

        User updatedUser =
                userService.updateUser(
                        email,
                        profileDTO
                );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Profile updated",

                        UserMapper.toUserDTO(
                                updatedUser
                        )
                )
        );
    }


    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookings(Authentication authentication){
        String email = authentication.getName();
        List<BookingDTO> bookings = userService.findAllBookings(email);
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Received All Bookings", bookings)
        );
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> getBookings(@PathVariable int id) throws BookingNotFoundException {

        BookingDTO booking = userService.findBookingById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Received Booking Details", booking)
        );
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<ApiResponse<BookingDTO>> cancelBookings(@PathVariable int id) throws BookingNotFoundException {

        BookingDTO booking = userService.cancelBooking(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Booking Canceled", booking)
        );
    }

    @PostMapping("/bookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<?>> createBooking(
            Authentication authentication,
            @RequestBody BookingRequestDTO request
    ) throws SeatAlreadyBookedException, ResourceNotFoundException, InsufficientWalletException {

        String email = authentication.getName();

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Booking successful",
                        userService.createBooking(email, request))
        );
    }



    @PutMapping("/wallet/add")
    public ResponseEntity<ApiResponse<?>>
    addMoneyToWallet(
            @RequestBody
            WalletDTO walletDTO,

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Money added to wallet",

                        userService
                                .addMoneyToWallet(
                                        authentication.getName(),
                                        walletDTO
                                )
                )
        );
    }

    @PostMapping(
            "/forgot-password"
    )
    public ResponseEntity<ApiResponse<?>>
    forgotPassword(
            @RequestBody
            ForgotPasswordDTO dto
    ) throws ResourceNotFoundException, MessagingException {

        userService.forgotPassword(
                dto.getEmail()
        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Reset link sent to email",

                        null
                )
        );
    }

    @PostMapping(
            "/reset-password"
    )
    public ResponseEntity<ApiResponse<?>>
    resetPassword(
            @RequestBody
            ResetPasswordDTO dto
    ) {

        userService.resetPassword(
                dto.getToken(),
                dto.getNewPassword()
        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Password reset successful",

                        null
                )
        );
    }

    @PutMapping(
            "/change-password"
    )
    public ResponseEntity<ApiResponse<?>>
    changePassword(

            Authentication authentication,

            @RequestBody
            ChangePasswordDTO dto
    ) {

        userService.changePassword(

                authentication.getName(),

                dto
        );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Password updated successfully",

                        null
                )
        );
    }


}
