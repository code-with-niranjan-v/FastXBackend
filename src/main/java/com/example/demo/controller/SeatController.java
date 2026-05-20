package com.example.demo.controller;

import com.example.demo.response.ApiResponse;
import com.example.demo.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping(
            "/booked-seats/{busId}"
    )
    public ResponseEntity<ApiResponse<?>>
    getBookedSeats(
            @PathVariable
            int busId,
            @RequestParam
            LocalDate date
    ) {

        return ResponseEntity.ok(

                new ApiResponse<>(

                        200,

                        true,

                        "Booked seats fetched",

                        seatService
                                .getBookedSeats(
                                        busId,date
                                )
                )
        );
    }
}
