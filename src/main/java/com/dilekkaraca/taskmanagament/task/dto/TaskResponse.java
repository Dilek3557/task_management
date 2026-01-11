package com.dilekkaraca.taskmanagament.task.dto;

import com.dilekkaraca.taskmanagament.task.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status
) {}
