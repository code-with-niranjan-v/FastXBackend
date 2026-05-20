package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Bus;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchBuses(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String date,
            @RequestParam(required = false) String time
    ) {

        List<Bus> buses = busService.searchBuses(origin, destination, date, time);

        return ResponseEntity.ok(
                new ApiResponse<>(200, true,
                        buses.isEmpty() ? "No buses found" : "Buses found",
                        buses)
        );
    }

    @GetMapping("/{busId}")
    public ResponseEntity<ApiResponse<?>> getBusById(@PathVariable int busId) throws ResourceNotFoundException {

        Bus bus = busService.getBusById(busId);

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Bus fetched", bus)
        );
    }


}
