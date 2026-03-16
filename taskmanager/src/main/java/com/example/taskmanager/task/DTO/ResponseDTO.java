package com.example.taskmanager.task.DTO;

import com.example.taskmanager.task.entity.Priority;
import com.example.taskmanager.task.entity.Status;
import com.example.taskmanager.users.User;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority ;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Long extension;


}
