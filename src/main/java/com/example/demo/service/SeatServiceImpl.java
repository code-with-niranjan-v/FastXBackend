package com.example.demo.service;

import com.example.demo.model.Seat;
import com.example.demo.repository.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeatServiceImpl  implements SeatService{
    @Autowired
    private  SeatRepo seatRepo;

    @Override
    public List<Integer>
    getBookedSeats(

            int busId,

            LocalDate journeyDate
    ) {

        List<Seat> seats =

                seatRepo
                        .findByBooking_Bus_BusIdAndBooking_JourneyDateAndBooking_StatusIn(

                                busId,

                                journeyDate,

                                List.of(

                                        "CONFIRMED",

                                        "CANCEL_REQUESTED"
                                )
                        );

        return seats.stream()

                .map(
                        Seat::getSeatNo
                )

                .toList();
    }
}
