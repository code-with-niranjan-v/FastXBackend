package com.example.demo.controller;

import com.example.demo.exception.UnAuthorizedException;
import com.example.demo.model.Bus;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operator/bus")
@PreAuthorize("hasRole('OPERATOR')||hasRole('ADMIN')")
public class OperatorBusController {

    @Autowired
    private BusService busService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addBus(
            @RequestBody Bus bus,
            Authentication authentication
    ) {
        Bus savedBus = busService.addBus(bus, authentication.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Bus added", savedBus)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateBus(
            @PathVariable int id,
            @RequestBody Bus bus,
            Authentication authentication
    ) throws UnAuthorizedException {
        Bus updatedBus = busService.updateBus(id, bus, authentication.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Bus updated", updatedBus)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBus(
            @PathVariable int id,
            Authentication authentication
    ) throws UnAuthorizedException {
        busService.deleteBus(id, authentication.getName());

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Bus deleted", null)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getMyBuses(Authentication authentication) {
        System.out.println(authentication.getName());
        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Buses fetched",
                        busService.getBusesByOperator(authentication.getName()))
        );
    }



}
