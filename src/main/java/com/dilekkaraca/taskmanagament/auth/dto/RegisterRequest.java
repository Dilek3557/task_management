package com.dilekkaraca.taskmanagament.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Name boş olamaz")
        @Size(min = 2, max = 100, message = "Name 2-100 karakter olmalı")
        String name,

        @NotBlank(message = "Email boş olamaz")
        @Email(message = "Email formatı geçersiz")
        String email,

        @NotBlank(message = "Password boş olamaz")
        @Size(min = 6, max = 64, message = "Password 6-64 karakter olmalı")
        String password
) { }
