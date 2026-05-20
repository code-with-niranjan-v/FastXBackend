package com.example.demo.exception;

public class RouteAssignedToBusException
        extends RuntimeException {

    public RouteAssignedToBusException() {
        super(
                "Route is assigned to one or more buses"
        );
    }
}
