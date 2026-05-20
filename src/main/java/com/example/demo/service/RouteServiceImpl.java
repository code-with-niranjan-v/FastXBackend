package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.RouteAssignedToBusException;
import com.example.demo.model.Bus;
import com.example.demo.model.Route;
import com.example.demo.repository.BusRepo;
import com.example.demo.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService{

    @Autowired
    private BusRepo busRepo;
    @Autowired
    private RouteRepo routeRepo;

    @Override
    public List<Route> findAllRoute() {
        return routeRepo.findAll();
    }

    @Override
    public Route findRouteById(int id) throws ResourceNotFoundException {
        return routeRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Route not Found"));
    }

    @Override
    public Route addRoute(Route route) {
        routeRepo.save(route);
        return route;
    }

    @Override
    public Route updateRoute(Route route) throws ResourceNotFoundException {
        if (!routeRepo.existsById(route.getRouteId())) {
            throw new ResourceNotFoundException("Cannot update. Route not found with id: " + route.getRouteId());
        }
        return routeRepo.save(route);
    }

    @Override
    public void deleteRoute(int id)
            throws ResourceNotFoundException {

        if(routeRepo.existsById(id)) {

            Route route =
                    routeRepo.findById(id)
                            .get();

            List<Bus> buses =
                    busRepo.findByRoute(route);

            if(!buses.isEmpty()){
                throw new RouteAssignedToBusException();
            }

            routeRepo.deleteById(id);

        } else {

            throw new ResourceNotFoundException(
                    "Route not found with ID: "
                            + id
            );
        }
    }

    @Override
    public Route updateRoute(
            int id,
            Route route
    ) throws ResourceNotFoundException {

        Route existingRoute =
                routeRepo.findById(id)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Route not found with ID: "
                                                        + id
                                        )
                        );

        existingRoute.setOrigin(
                route.getOrigin()
        );

        existingRoute.setDestination(
                route.getDestination()
        );

        existingRoute.setStartDateTime(
                route.getStartDateTime()
        );

        return routeRepo.save(
                existingRoute
        );
    }
}
