package com.example.demo.service;


import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class DataService {

    private final UserRepo userRepo;
    private final BusRepo busRepo;
    private final RouteRepo routeRepo;
    private final BookingRepo bookingRepo;
    private final SeatRepo seatRepo;
    private final RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public DataService(UserRepo userRepo,
                       BusRepo busRepo,
                       RoleRepo roleRepo,
                       RouteRepo routeRepo,
                       BookingRepo bookingRepo,
                       SeatRepo seatRepo) {
        this.userRepo = userRepo;
        this.busRepo = busRepo;
        this.routeRepo = routeRepo;
        this.bookingRepo = bookingRepo;
        this.seatRepo = seatRepo;
        this.roleRepo = roleRepo;
    }

    @Transactional
    public void loadData() {

        roleRepo.save(new Role(0, "USER"));
        roleRepo.save(new Role(0, "OPERATOR"));
        roleRepo.save(new Role(0, "ADMIN"));
        Role userRole = roleRepo.findByRole("USER");
        Role operatorRole = roleRepo.findByRole("OPERATOR");
        Role adminRole = roleRepo.findByRole("ADMIN");
        User user = new User();
        user.setName("Niranjan");
        user.setPhoneNumber("9876543210");
        user.setEmail("niranjan@gmail.com");
        user.setGender("Male");
        user.setAddress("Chennai");
        user.setWallet(1000);
        user.setPassword("1234");
        user.setRole(userRole);
        userRepo.save(user);

        User operator = new User();
        operator.setName("ABC Travels");
        operator.setPhoneNumber("9999999999");
        operator.setEmail("abc@travels.com");
        operator.setGender("Male");
        operator.setAddress("Chennai");
        operator.setWallet(50000);
        operator.setPassword("1234");
        operator.setRole(operatorRole);
        userRepo.save(operator);

        Route route = new Route();
        route.setOrigin("Chennai");
        route.setDestination("Bangalore");
        route.setStartDateTime(LocalDateTime.now().plusDays(1));
        routeRepo.save(route);

        Bus bus = new Bus();
        bus.setName("Volvo AC");
        bus.setBusNumber("TN01AB1234");
        bus.setBusType("AC Sleeper");
        bus.setNoOfSeats(40);
        bus.setFare(800);
        bus.setWaterBottle(true);
        bus.setBlanket(true);
        bus.setChargingPoint(true);
        bus.setTv(false);
        bus.setRoute(route);
        bus.setBusOperator(operator);
        busRepo.save(bus);

        Booking booking = new Booking();
        booking.setTotalFare(1600);
        booking.setTotalNoOfSeats(2);
        booking.setStatus("CONFIRMED");
        booking.setUser(user);
        booking.setBus(bus);
        booking.setBusOperator(operator);
        booking.setJourneyDate(LocalDate.now());
        bookingRepo.save(booking);


        Seat s1 = new Seat();
        s1.setSeatNo(1);
        s1.setBooking(booking);

        Seat s2 = new Seat();
        s2.setSeatNo(2);
        s2.setBooking(booking);

        seatRepo.saveAll(Arrays.asList(s1, s2));

        User admin =
                userRepo.findByEmail(
                        "admin@fastx.com"
                );

        if(admin == null){


            User newAdmin =
                    new User();

            newAdmin.setName(
                    "Admin"
            );

            newAdmin.setEmail(
                    "admin@fastx.com"
            );

            newAdmin.setPassword(
                    passwordEncoder.encode(
                            "admin123"
                    )
            );

            newAdmin.setRole(adminRole);

            newAdmin.setWallet(0);

            newAdmin.setPhoneNumber(
                    "124567890"
            );

            newAdmin.setGender(
                    "Male"
            );

            newAdmin.setAddress(
                    "FastX Headquarters"
            );

            userRepo.save(newAdmin);

            System.out.println(
                    "Admin account created"
            );
        }
    }
}