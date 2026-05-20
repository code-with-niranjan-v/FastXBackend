package com.example.demo.repository;

import com.example.demo.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Integer> {

    List<Seat>
    findByBooking_Bus_BusIdAndBooking_JourneyDateAndBooking_StatusIn(

            int busId,

            LocalDate journeyDate,

            List<String> statuses
    );

}
