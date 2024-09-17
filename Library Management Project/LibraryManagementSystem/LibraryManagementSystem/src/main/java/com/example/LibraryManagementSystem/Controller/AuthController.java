package com.example.LibraryManagementSystem.Controller;

import com.example.LibraryManagementSystem.Service.AuthService;
import com.example.LibraryManagementSystem.request.JwtResponse;
import com.example.LibraryManagementSystem.request.LoginRequest;
import com.example.LibraryManagementSystem.request.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/librarian/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> authenticateLibrarian(@RequestBody LoginRequest loginRequest) {
        log.info("request to login");

        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerLibrarian(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("request to sign-up");

        authService.signUp(signUpRequest);
        return ResponseEntity.ok("Librarian registered successfully!");
    }
}
