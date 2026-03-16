package com.example.taskmanager.task.entity;

import com.example.taskmanager.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority ;

    @CreationTimestamp
    private LocalDate createdAt;

    private LocalDate dueDate;
    private Long extension=0L;

    @ManyToOne
    @JoinColumn(name ="assigned_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name ="assigned_to_id")
    private User assignedTo;



}
