package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.RouteNotFoundException;
import com.example.demo.model.Route;
import com.example.demo.repository.RouteRepo;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Route>>> getAllRoutes(){
        return ResponseEntity.ok(new ApiResponse<>(200,true,"Received All Routes",routeService.findAllRoute()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Route>> getAllRouteById(@PathVariable int id) throws RouteNotFoundException, ResourceNotFoundException {
        Route route = routeService.findRouteById(id);
        if(route==null){
            throw new RouteNotFoundException();
        }
        return ResponseEntity.ok(new ApiResponse<>(200,true,"Received All Routes",route));
    }

    @PreAuthorize("hasRole('ADMIN')||hasRole('OPERATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> addRoute(@RequestBody Route route) {

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Route added",
                        routeService.addRoute(route))
        );
    }

    @PreAuthorize("hasRole('ADMIN')||hasRole('OPERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteRoute(@PathVariable int id) throws ResourceNotFoundException {

        routeService.deleteRoute(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, true, "Route deleted", null)
        );
    }

    @PreAuthorize("hasRole('ADMIN')||hasRole('OPERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateRoute(
            @PathVariable int id,
            @RequestBody Route route
    ) throws ResourceNotFoundException {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        true,
                        "Route updated",
                        routeService.updateRoute(id, route)
                )
        );
    }


}
