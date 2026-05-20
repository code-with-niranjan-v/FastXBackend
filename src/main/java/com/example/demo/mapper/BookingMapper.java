package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.Bus;
import com.example.demo.model.Seat;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static List<BookingDTO>
    toListOfBookingDTO(
            List<Booking> bookings
    ) {

        List<BookingDTO> bookingDTOS =
                new ArrayList<>();

        if(bookings != null){

            bookings.forEach(
                    b ->
                            bookingDTOS.add(
                                    BookingMapper
                                            .toBookingDTO(b)
                            )
            );
        }

        return bookingDTOS;
    }

    public static BookingDTO
    toBookingDTO(
            Booking booking
    ) {

        List<Integer> seatNumbers =
                booking.getSeats() != null
                        ? booking.getSeats()
                        .stream()
                        .map(
                                Seat::getSeatNo
                        )
                        .toList()
                        : new ArrayList<>();

        return new BookingDTO(

                booking.getBookingId(),

                booking.getTotalFare(),

                booking.getTotalNoOfSeats(),

                booking.getStatus(),

                booking.getBus()
                        .getName(),

                booking.getBus()
                        .getRoute()
                        .getOrigin(),

                booking.getBus()
                        .getRoute()
                        .getDestination(),

                booking.getBus()
                        .getRoute()
                        .getStartDateTime()
                        .toString(),

                booking.getBusOperator()
                        .getName(),

                booking.getBus()
                        .getBusId(),

                booking.getUser()
                        .getUserId(),

                booking.getBusOperator()
                        .getUserId(),

                booking.getUser()
                        .getName(),

                seatNumbers,
                booking.getJourneyDate()
        );
    }

    public static Booking
    toBooking(
            BookingDTO bookingDTO
    ) {

        Booking booking =
                new Booking();

        booking.setBookingId(
                bookingDTO.getBookingId()
        );

        booking.setTotalFare(
                bookingDTO.getTotalFare()
        );

        booking.setTotalNoOfSeats(
                bookingDTO.getTotalNoOfSeats()
        );

        booking.setStatus(
                bookingDTO.getStatus()
        );

        Bus bus = new Bus();

        bus.setBusId(
                bookingDTO.getBusId()
        );

        booking.setBus(bus);

        User operator = new User();

        operator.setUserId(
                bookingDTO.getOperatorId()
        );

        booking.setBusOperator(
                operator
        );

        User user = new User();

        user.setUserId(
                bookingDTO.getUserId()
        );

        booking.setUser(user);

        return booking;
    }

    public static List<Booking>
    toListOfBooking(
            List<BookingDTO> bookingDTOS
    ) {

        List<Booking> bookings =
                new ArrayList<>();

        if(bookingDTOS != null){

            bookingDTOS.forEach(
                    b ->
                            bookings.add(
                                    BookingMapper
                                            .toBooking(b)
                            )
            );
        }

        return bookings;
    }
}