package com.dilekkaraca.taskmanagament.task.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskUpdateRequest(
        @NotBlank String title,
        String description
) {}
