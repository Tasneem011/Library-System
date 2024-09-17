package com.example.LibraryManagementSystem.Service;

import com.example.LibraryManagementSystem.request.JwtResponse;
import com.example.LibraryManagementSystem.request.LoginRequest;
import com.example.LibraryManagementSystem.request.SignupRequest;

public interface AuthServiceImpl {


    void signUp(SignupRequest request);

    JwtResponse login(LoginRequest request);
}
