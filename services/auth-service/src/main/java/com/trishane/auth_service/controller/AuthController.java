package com.trishane.auth_service.controller;

import com.trishane.auth_service.dto.request.LoginRequest;
import com.trishane.auth_service.dto.request.RegisterRequest;
import com.trishane.auth_service.dto.response.ErrorResponse;
import com.trishane.auth_service.dto.response.LoginResponse;
import com.trishane.auth_service.dto.response.SuccessResponse;
import com.trishane.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user based on the provided login credentials.
     * <p>
     *     This method processes a POST request to the "/login" endpoint.
     *     It validates the user's credentials and returns a response with a JWT token if authentication is successful.
     * </p>
     *
     * @param request the {@link LoginRequest} object containing the user's login credentials
     * @return a {@link ResponseEntity} containing a success message and JWT token, or an error response
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try{
            String token = authService.authenticate(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse("Login successful", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred"));
        }
    }

    /**
     * Handles user registration requests
     * <p>
     *     This method processes a POST request to the "/register" endpoint.
     *     It validates the user input and registers a new user if the input is valid.
     * </p>
     * @param request the {@link RegisterRequest} object containing the user's registration details
     * @return a {@link ResponseEntity} containing a success message or error details
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred"));
        }
    }
}