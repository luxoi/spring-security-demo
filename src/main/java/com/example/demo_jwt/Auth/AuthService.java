package com.example.demo_jwt.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo_jwt.Jwt.JwtService;
import com.example.demo_jwt.User.Role;
import com.example.demo_jwt.User.User;
import com.example.demo_jwt.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
       UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user =  User.builder()
             .username(request.getUsername())
             .password(passwordEncoder.encode( request.getPassword()))
             .firstname(request.getFirstname())
             .lastname(request.getLastname())
             .country(request.getCountry())
             .role(Role.USER)
             .build();

             if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new IllegalArgumentException("El usuario ya existe con este correo: " + user.getUsername());
            }else{
                // Insertar el usuario
                userRepository.save(user);
            }
      

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }

}
