package com.example.taskmanager.task.repository;

import com.example.taskmanager.task.entity.Status;
import com.example.taskmanager.task.entity.Task;
import com.example.taskmanager.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {

    Page<Task> findByAssignedTo(User user , Pageable pageable);
    Page<Task> findByAssignedToAndStatus(User user , Status status , Pageable pageable);

    List<Task> findByDueDateBefore(LocalDate date);

    Page<Task> findByStatus(Status status , Pageable pageable);

}
