package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exception.BookingNotFoundException;
import com.example.demo.exception.InsufficientWalletException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SeatAlreadyBookedException;
import com.example.demo.mapper.BookingMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private EmailService emailService;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SeatRepo seatRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BusRepo busRepo;

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    @Override
    public void changePassword(
            String email,
            ChangePasswordDTO dto
    ) {

        User user =
                userRepo.findByEmail(
                        email
                );

        if(user == null){

            throw new UsernameNotFoundException(
                    "User not found"
            );
        }

        boolean matches =
                passwordEncoder.matches(

                        dto.getCurrentPassword(),

                        user.getPassword()
                );

        if(!matches){

            throw new RuntimeException(
                    "Current password is incorrect"
            );
        }

        user.setPassword(

                passwordEncoder.encode(
                        dto.getNewPassword()
                )
        );

        userRepo.save(user);
    }
    @Override
    public User updateUser(
            String email,
            ProfileDTO dto
    ) {

        User user =
                userRepo.findByEmail(
                        email
                );

        if(user == null){

            throw new UsernameNotFoundException(
                    "User not found"
            );
        }

        user.setName(
                dto.getName()
        );

        user.setEmail(
                dto.getEmail()
        );

        user.setPhoneNumber(
                dto.getPhoneNumber()
        );

        user.setAddress(
                dto.getAddress()
        );

        user.setGender(
                dto.getGender()
        );

        return userRepo.save(user);
    }

    @Override
    public List<BookingDTO> findAllBookings(String email) {
        User user = userRepo.findByEmail(email);
        List<Booking> bookings = bookingRepo.findAllBookingsByUser(user);
        return BookingMapper.toListOfBookingDTO(bookings);
    }

    @Override
    public BookingDTO findBookingById(int id) throws BookingNotFoundException {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if(booking!=null){
            return BookingMapper.toBookingDTO(booking);
        }else{
            throw new BookingNotFoundException(id);
        }

    }

    @Transactional
    @Override
    public BookingDTO cancelBooking(int id)
            throws BookingNotFoundException {

        Booking booking =
                bookingRepo.findById(id)
                        .orElseThrow(
                                () ->
                                        new BookingNotFoundException(
                                                id
                                        )
                        );

        if(
                booking.getStatus()
                        .equals("CANCELLED")
        ) {

            throw new RuntimeException(
                    "Booking already cancelled"
            );
        }

        if(
                booking.getStatus()
                        .equals(
                                "CANCEL_REQUESTED"
                        )
        ) {

            throw new RuntimeException(
                    "Cancellation request already sent"
            );
        }

        booking.setStatus(
                "CANCEL_REQUESTED"
        );

        bookingRepo.save(booking);

        Refund refund = new Refund();

        refund.setAmount(
                booking.getTotalFare()
        );

        refund.setStatus("PENDING");

        refund.setBook(booking);

        refund.setBusOperator(
                booking.getBusOperator()
        );

        refund.setUser(
                booking.getUser()
        );

        refundRepo.save(refund);

        return BookingMapper.toBookingDTO(
                booking
        );
    }

    @Transactional
    @Override
    public BookingDTO createBooking(String email, BookingRequestDTO request) throws SeatAlreadyBookedException, ResourceNotFoundException, InsufficientWalletException {
        User user = userRepo.findByEmail(email);

        Bus bus = busRepo.findById(request.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found"));
        User operator = bus.getBusOperator();
        List<Seat> bookedSeats =

                seatRepo
                        .findByBooking_Bus_BusIdAndBooking_JourneyDateAndBooking_StatusIn(

                                bus.getBusId(),

                                request.getJourneyDate(),

                                List.of(

                                        "CONFIRMED",

                                        "CANCEL_REQUESTED"
                                )
                        );

        List<Integer> bookedSeatNumbers = bookedSeats.stream()
                .map(Seat::getSeatNo)
                .toList();


        for (Integer seatNo : request.getSeatNumbers()) {
            if (bookedSeatNumbers.contains(seatNo)) {
                throw new SeatAlreadyBookedException("Seat already booked: " + seatNo);
            }
        }
        double totalFare =
                bus.getFare()
                        * request.getSeatNumbers().size();

        if(user.getWallet() < totalFare) {

            throw new InsufficientWalletException();
        }

        user.setWallet(
                user.getWallet()
                        - totalFare
        );

        operator.setWallet(
                operator.getWallet()
                        + totalFare
        );

        userRepo.save(user);

        userRepo.save(operator);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setStatus("CONFIRMED");
        booking.setTotalNoOfSeats(request.getSeatNumbers().size());
        booking.setBusOperator(bus.getBusOperator());
        booking.setTotalFare(bus.getFare() * request.getSeatNumbers().size());
        booking.setJourneyDate(
                request.getJourneyDate()
        );
        bookingRepo.save(booking);

        List<Seat> seats = request.getSeatNumbers().stream().map(seatNo -> {
            Seat s = new Seat();
            s.setSeatNo(seatNo);
            s.setBooking(booking);
            return s;
        }).toList();
        booking.setSeats(seats);
        seatRepo.saveAll(seats);

        String formattedArrivalTime =
                bus.getRoute().getStartDateTime()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "hh:mm a"
                                )
                        );

        emailService.sendTicketEmail(

                user.getEmail(),

                booking.getBookingId()+"",

                bus.getRoute().getOrigin(),

                bus.getRoute().getDestination(),

                request.getJourneyDate().toString(),

                bus.getName(),

                request.getSeatNumbers().toString(),

                formattedArrivalTime

        );

        return BookingMapper.toBookingDTO(booking);
    }

    @Override
    public UserDTO addMoneyToWallet(
            String email,
            WalletDTO walletDTO
    ) {

        User user =
                userRepo.findByEmail(
                        email
                );

        user.setWallet(
                user.getWallet()
                        + walletDTO.getAmount()
        );

        userRepo.save(user);

        return UserMapper
                .toUserDTO(user);
    }

    @Override
    public void forgotPassword(
            String email
    ) throws ResourceNotFoundException, MessagingException {

        User user =
                userRepo.findByEmail(
                        email
                );

        if(user == null){

            throw new ResourceNotFoundException(
                    "User not found"
            );
        }

        String token =
                UUID.randomUUID()
                        .toString();

        user.setResetToken(
                token
        );

        user.setResetTokenExpiry(
                LocalDateTime.now()
                        .plusMinutes(15)
        );

        userRepo.save(user);

        String resetLink =
                "http://localhost:5173/reset-password?token="
                        + token;

        emailService.sendForgotPasswordEmail(
                user.getEmail(),
                user.getName(),
                resetLink
        );
    }

    @Override
    public void resetPassword(
            String token,
            String newPassword
    ) {

        User user =
                userRepo.findByResetToken(
                        token
                );

        if(user == null){

            throw new RuntimeException(
                    "Invalid reset token"
            );
        }

        if(
                user
                        .getResetTokenExpiry()
                        .isBefore(
                                LocalDateTime.now()
                        )
        ){

            throw new RuntimeException(
                    "Reset token expired"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        newPassword
                )
        );

        user.setResetToken(
                null
        );

        user.setResetTokenExpiry(
                null
        );

        userRepo.save(user);
    }

}
