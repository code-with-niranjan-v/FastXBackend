package com.example.demo.mapper;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO toUserDTO(User user){
        return new UserDTO(user.getUserId(), user.getName(), user.getPhoneNumber(), user.getEmail(), user.getGender(), user.getAddress(), user.getWallet(), BookingMapper.toListOfBookingDTO(user.getBookings()),user.getRefunds(),user.isActive(),user.getBookings().size());
    }

    public static List<UserDTO> toUserDTOS(List<User> users){
        List<UserDTO> userDTOS = new ArrayList<>();
        if(users!=null){
            users.forEach(u->userDTOS.add(UserMapper.toUserDTO(u)));
        }
        return userDTOS;
    }

    public static User toUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setBookings(BookingMapper.toListOfBooking(userDTO.getBookings()));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        user.setAddress(userDTO.getAddress());
        user.setWallet(userDTO.getWallet());
        user.setRefunds(userDTO.getRefunds());
        user.setActive(userDTO.isActive());
        return user;

    }

    public static List<User> toListOfUsers(List<UserDTO> userDTOS){
        List<User> users = new ArrayList<>();
        userDTOS.forEach(u->users.add(UserMapper.toUser(u)));
        return users;
    }

    public static User registerDtoToUser(RegisterDTO registerDTO,Role role){
        User user = new User();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setGender(registerDTO.getGender());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setAddress(registerDTO.getAddress());
        user.setWallet(registerDTO.getWallet());
        user.setPassword(registerDTO.getPassword());
        user.setRole(role);
        return user;
    }

    public static List<UserDTO>
    toListOfUserDTO(
            List<User> users
    ) {

        List<UserDTO> userDTOS =
                new ArrayList<>();

        if(users != null){

            users.forEach(
                    user ->
                            userDTOS.add(
                                    UserMapper
                                            .toUserDTO(user)
                            )
            );
        }

        return userDTOS;
    }
}
