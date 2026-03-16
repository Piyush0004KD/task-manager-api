package com.example.taskmanager.task.service;

import com.example.taskmanager.task.*;
import com.example.taskmanager.task.DTO.Mapper;
import com.example.taskmanager.task.DTO.RequestDTO;
import com.example.taskmanager.task.DTO.ResponseDTO;
import com.example.taskmanager.task.entity.Status;
import com.example.taskmanager.task.entity.Task;
import com.example.taskmanager.task.repository.TaskRepo;
import com.example.taskmanager.users.Role;
import com.example.taskmanager.users.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.taskmanager.users.User;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;



@Service
@Transactional
public class TaskService{

    private final TaskRepo taskRepository;
    private final UserRepo userRepository;
    private final Mapper mapper;


    TaskService(TaskRepo taskRepository,UserRepo userRepository,  Mapper mapper){
        this.userRepository= userRepository;
        this.mapper=mapper;
        this.taskRepository= taskRepository;
    }

    public ResponseDTO createTask(RequestDTO requestTask){
        User currentUser = getCurrentUser();
        User assignedTo = userRepository.findById(requestTask.getAssignedToId()).orElseThrow(()-> new UserNotFoundException("not able to found"));

       Task task = mapper.DTOtoEntity(requestTask);

        task.setCreatedBy(currentUser);
        task.setStatus(Status.PENDING);

         if(currentUser.getRole() == Role.USER && !currentUser.getId().equals(assignedTo.getId())){
            throw new UnauthorizeAccessException("u cant assign task");
        }

            task.setAssignedTo(assignedTo);

     taskRepository.save(task);

        return mapper.entityToDTO(task);
    }


    public ResponseDTO getMyTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("invalid id"));
        User currentUser = getCurrentUser();
        if(!task.getAssignedTo().getId().equals(currentUser.getId())){
            throw new UnauthorizeAccessException("cant acess other users task");
        }
        return mapper.entityToDTO(task);
    }
    public User getCurrentUser(){
        return (User)SecurityContextHolder
                  .getContext()
                  .getAuthentication()
                  .getPrincipal();

    }

@PreAuthorize("hasRole('ADMIN')")
    public Page<ResponseDTO> getAllTasks(int page , int size) {
    Pageable pageable= PageRequest.of(page,size);
        Page<ResponseDTO> task = taskRepository.findAll(pageable).map(mapper::entityToDTO);

        return task;

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseDTO> getOverDueTask(){
        List<Task> overDueTask = taskRepository.findByDueDateBefore(LocalDate.now());
        return overDueTask
                .stream()
                .map(mapper :: entityToDTO)
                .toList();

    }

    public Page<ResponseDTO> getTaskByStatus(Status status , int page , int size){
        User currentUser = getCurrentUser();
        Pageable pageable = PageRequest.of(page,size);

        if(currentUser.getRole() == Role.ADMIN){
            return taskRepository.findByStatus(status , pageable).map(mapper::entityToDTO);
        }
        return taskRepository.findByAssignedToAndStatus(currentUser , status , pageable).map(mapper::entityToDTO);
    }

    public Page<ResponseDTO> getAllMyTask(int page , int size){

        Pageable pageable = PageRequest.of(page , size);
        User currentuser = getCurrentUser();
      return taskRepository.findByAssignedTo(currentuser , pageable).map(mapper::entityToDTO);

    }

    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("invalid id"));
        User currentUser = getCurrentUser();
       if(currentUser.getRole()==Role.ADMIN ||
               task.getCreatedBy().getId().equals(currentUser.getId())) {
           taskRepository.delete(task);
       }else{
           throw new UnauthorizeAccessException("u are not allowed to do that");
       }
    }

    public ResponseDTO updateTask(Long id, RequestDTO updatedTask) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Invalid task id"));

        User currentUser = getCurrentUser();
        boolean updated = false;

        // USER logic
        if (currentUser.getRole() == Role.USER) {

            // user updating their own task
            if (task.getCreatedBy().getId().equals(currentUser.getId())) {

                if (updatedTask.getTitle() != null) {
                    task.setTitle(updatedTask.getTitle());
                    updated = true;
                }

                if (updatedTask.getDescription() != null) {
                    task.setDescription(updatedTask.getDescription());
                    updated = true;
                }

                if (updatedTask.getPriority() != null) {
                    task.setPriority(updatedTask.getPriority());
                    updated = true;
                }

                if (updatedTask.getStatus() != null) {
                    task.setStatus(updatedTask.getStatus());
                    updated = true;
                }
            }

            // task assigned by admin → user can only update status
            else if (task.getAssignedTo().getId().equals(currentUser.getId())) {

                if (updatedTask.getStatus() != null) {
                    task.setStatus(updatedTask.getStatus());
                    updated = true;
                } else {
                    throw new UnauthorizeAccessException("You can only update task status");
                }
            }

            else {
                throw new AccessDeniedException("You cannot modify this task");
            }
        }

        // ADMIN logic
        else if (currentUser.getRole() == Role.ADMIN) {

            if (!task.getCreatedBy().getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("You cannot modify another admin's task");
            }

            if (updatedTask.getTitle() != null) {
                task.setTitle(updatedTask.getTitle());
                updated = true;
            }

            if (updatedTask.getDescription() != null) {
                task.setDescription(updatedTask.getDescription());
                updated = true;
            }

            if (updatedTask.getPriority() != null) {
                task.setPriority(updatedTask.getPriority());
                updated = true;
            }

            if (updatedTask.getStatus() != null) {
                task.setStatus(updatedTask.getStatus());
                updated = true;
            }

            if (updatedTask.getDueDate() != null) {

                LocalDate oldDueDate = task.getDueDate();
                LocalDate newDueDate = updatedTask.getDueDate();

                if (newDueDate.isBefore(oldDueDate)) {
                    throw new InvalidDueDateException("Due date cannot be reduced");
                }

                long extensionDays = ChronoUnit.DAYS.between(oldDueDate, newDueDate);

                task.setExtension(extensionDays);
                task.setDueDate(newDueDate);

                updated = true;
            }
        }

        if (!updated) {
            throw new NoChangeDetectedException("No changes detected");
        }

        taskRepository.save(task);
        return mapper.entityToDTO(task);
    }

}
