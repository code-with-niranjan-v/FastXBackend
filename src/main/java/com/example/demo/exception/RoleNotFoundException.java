package com.example.demo.exception;

public class RoleNotFoundException extends Exception{
    public RoleNotFoundException(String role){
        super("No role Such as "+role+" was found!");
    }
}
