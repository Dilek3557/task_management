package com.dilekkaraca.taskmanagament.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "email boş olamaz")
        @Email(message = "email formatinda girniz")
        String email,


        @NotBlank(message = "password boş olamaz")
        String password) {
}
