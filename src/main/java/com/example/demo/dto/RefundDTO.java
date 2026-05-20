package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {

    private int refundId;

    private double amount;

    private String status;

    private int bookingId;

    private String passengerName;

    private String origin;

    private String destination;
}
