package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.response.ApiResponse;
import com.example.demo.security.service.UserPrincipal;
import com.example.demo.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    public ResponseEntity<ApiResponse<?>> registerUser(RegisterDTO registerDTO) throws RoleNotFoundException {
        Role role = roleRepo.findByRole(registerDTO.getRole());
        if(role==null){
            throw new RoleNotFoundException(registerDTO.getRole());
        }
        User user = UserMapper.registerDtoToUser(registerDTO,role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(new ApiResponse<>(200,true,"User registered Successfully",registerDTO.getEmail()));
    }

    public ResponseEntity<ApiResponse<?>> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

        if(authentication.isAuthenticated()){
            UserPrincipal user =
                    (UserPrincipal) authentication.getPrincipal();
            String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
            if(!user.getUser().isActive()){

                throw new RuntimeException(
                        "Account has been disabled"
                );
            }
            return ResponseEntity.ok(new ApiResponse<>(200,true,"Login Successfull", Map.of("token",jwtUtil.generateToken(loginDTO.getEmail(),role),"role",role,"name",user.getName(),"email",user.getUsername(),"phoneNumber",user.getPhoneNumber(),"wallet",user.getUser().getWallet())));
        }else{
            return ResponseEntity.badRequest().body(new ApiResponse<String>(401,false,"Login Failed",""));
        }
    }
}
