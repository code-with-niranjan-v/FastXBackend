package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatorBookingDTO {

    private int bookingId;

    private String passenger;

    private String route;

    private int seats;

    private double amount;

    private String status;
}