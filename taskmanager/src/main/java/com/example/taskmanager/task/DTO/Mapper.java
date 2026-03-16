package com.example.taskmanager.task.DTO;

import com.example.taskmanager.task.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Mapper {


    public Task DTOtoEntity(RequestDTO requestDTO){
        if(requestDTO==null)return null;

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setPriority(requestDTO.getPriority());
        task.setDueDate(requestDTO.getDueDate());

        return task;

    }

    public ResponseDTO entityToDTO(Task task){

        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setId(task.getId());
        responseDTO.setTitle(task.getTitle());
        responseDTO.setDescription(task.getDescription());
        responseDTO.setPriority(task.getPriority());
        responseDTO.setStatus(task.getStatus());
        responseDTO.setCreatedAt(task.getCreatedAt());
        responseDTO.setDueDate(task.getDueDate());
        responseDTO.setExtension(task.getExtension());

        return responseDTO;
    }


}
