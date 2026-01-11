package com.dilekkaraca.taskmanagament.task.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskCreateRequest (
    @NotBlank
    String title,
    String description

)
{}
