package com.example.demo.service;

import com.example.demo.dto.OperatorBookingDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingRepo bookingRepo;

    @Override
    public List<OperatorBookingDTO> getAllBookings(User user) {
        List<Booking> bookings = bookingRepo.findAllBookingByBusOperator(user);
        return bookings.stream().map(
                booking -> new OperatorBookingDTO(

                        booking.getBookingId(),

                        booking.getUser().getName(),

                        booking.getBus()
                                .getRoute()
                                .getOrigin()

                                + " → " +

                                booking.getBus()
                                        .getRoute()
                                        .getDestination(),

                        booking.getTotalNoOfSeats(),

                        booking.getTotalFare(),

                        booking.getStatus()
                )
        ).toList();
    }
}
