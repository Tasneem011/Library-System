package com.example.LibraryManagementSystem.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
        @NotBlank(message = "username should not be null and must have at least one non-whitespace character")
        String username,

        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "password should has at least one letter and one digit and 6 characters in length") //ensure that's at least one letter and one digit and 6 characters in length
        String password
) {


}
