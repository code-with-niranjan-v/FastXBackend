package com.example.demo.exception;

public class RouteNotFoundException extends Exception{
    public RouteNotFoundException(){
        super("Route Not Found");
    }
}
