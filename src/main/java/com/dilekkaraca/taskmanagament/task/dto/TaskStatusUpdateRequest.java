package com.dilekkaraca.taskmanagament.task.dto;

import com.dilekkaraca.taskmanagament.task.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateRequest(
        @NotNull TaskStatus status
) {}
