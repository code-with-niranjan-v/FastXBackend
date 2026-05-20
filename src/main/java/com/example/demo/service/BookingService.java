package com.example.demo.service;

import com.example.demo.dto.OperatorBookingDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.User;

import java.util.List;

public interface BookingService {

    List<OperatorBookingDTO> getAllBookings(User user);
}
