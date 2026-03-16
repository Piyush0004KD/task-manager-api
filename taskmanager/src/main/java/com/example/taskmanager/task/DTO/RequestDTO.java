package com.example.taskmanager.task.DTO;

import com.example.taskmanager.task.entity.Priority;
import com.example.taskmanager.task.entity.Status;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.time.LocalDate;


@Data
public class RequestDTO {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    @NotNull(message = "Status cannot be null")
    private Status status;

    @NotNull(message = "Due date cannot be null")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @NotNull(message = "AssignedTo cannot be null")
    private Long assignedToId;
}
