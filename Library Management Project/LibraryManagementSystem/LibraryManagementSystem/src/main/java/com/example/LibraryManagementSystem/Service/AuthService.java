package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.Repository.LibrarianRepository;
import com.example.LibraryManagementSystem.Security.JWT.JwtService;
import com.example.LibraryManagementSystem.entity.Librarian;
import com.example.LibraryManagementSystem.request.JwtResponse;
import com.example.LibraryManagementSystem.request.LoginRequest;
import com.example.LibraryManagementSystem.request.SignupRequest;
import com.example.LibraryManagementSystem.validation.LibrarianNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor

public class AuthService implements AuthServiceImpl {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final LibrarianRepository librarianRepository;


    @Override
    public void signUp(SignupRequest request) {
        Librarian librarian = new Librarian();
        librarian.setUsername(request.username());
        librarian.setPassword(passwordEncoder.encode(request.password()));
        librarianRepository.save(librarian);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Librarian librarian = librarianRepository.findByUsername(request.username()).orElseThrow(() ->new LibrarianNotFoundException(request.username()));

        User user= new User(librarian.getUsername(),passwordEncoder.encode(librarian.getPassword()), Collections.singleton(new SimpleGrantedAuthority("Librarian")));

        var jwtToken = jwtService.generateToken(user);
        return new JwtResponse(jwtToken);
    }
}