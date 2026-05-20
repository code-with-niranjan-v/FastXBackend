package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SeatService {
    List<Integer>
    getBookedSeats(int busId, LocalDate journeyDate);
}
