package com.example.demo.repository;

import com.example.demo.model.Booking;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Integer> {
    List<Booking> findAllBookingsByUser(User user);

    List<Booking> findAllBookingByBusOperator(User user);
}
