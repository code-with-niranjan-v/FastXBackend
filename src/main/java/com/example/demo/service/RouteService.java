package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {

    List<Route> findAllRoute();
    Route findRouteById(int id) throws ResourceNotFoundException;
    Route addRoute(Route route);
    Route updateRoute(Route route) throws ResourceNotFoundException;

    void deleteRoute(int id) throws ResourceNotFoundException;
    Route updateRoute(int id, Route route)
            throws ResourceNotFoundException;

}
