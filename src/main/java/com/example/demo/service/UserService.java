package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.InsufficientWalletException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SeatAlreadyBookedException;
import com.example.demo.model.Booking;
import com.example.demo.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(User user);
    User findUserById(int id);
    List<User> getAllUsers();
    User getUserByEmail(String email);

    User updateUser(String email, ProfileDTO userDTO);

    List<BookingDTO> findAllBookings(String email);

    BookingDTO findBookingById(int id) throws BookingNotFoundException;
    UserDTO addMoneyToWallet(
            String email,
            WalletDTO walletDTO
    );
    BookingDTO cancelBooking(int id) throws BookingNotFoundException;
    void forgotPassword(String email ) throws ResourceNotFoundException, MessagingException;
    void changePassword(
            String email,
            ChangePasswordDTO dto
    );
    void resetPassword(String token, String newPassword);
    BookingDTO createBooking(String email, BookingRequestDTO request) throws SeatAlreadyBookedException, ResourceNotFoundException, InsufficientWalletException;
}
