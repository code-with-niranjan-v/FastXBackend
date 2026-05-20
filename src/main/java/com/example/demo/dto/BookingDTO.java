package com.example.demo.dto;

import com.example.demo.model.Bus;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private int bookingId;

    private double totalFare;

    private int totalNoOfSeats;

    private String status;

    private String busName;

    private String origin;

    private String destination;

    private String startDateTime;

    private String operatorName;

    private int busId;

    private int userId;

    private int operatorId;

    private String passengerName;

    private List<Integer> seatNumbers;

    private LocalDate journeyDate = null;


}
