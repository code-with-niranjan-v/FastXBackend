package com.example.demo.dto;


import com.example.demo.model.Refund;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private Integer userId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    private String address;

    @Positive(message = "Wallet must be positive")
    private double wallet;

    @JsonIgnore
    private List<BookingDTO> bookings;
    @JsonIgnore
    private List<Refund> refunds;

    private boolean active;

    private int bookingsCount;

}
