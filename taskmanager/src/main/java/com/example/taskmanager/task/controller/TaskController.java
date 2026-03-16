package com.example.taskmanager.task.controller;


import com.example.taskmanager.task.DTO.RequestDTO;
import com.example.taskmanager.task.DTO.ResponseDTO;
import com.example.taskmanager.task.entity.Status;
import com.example.taskmanager.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


@PostMapping
public ResponseEntity<ResponseDTO> addTask(@RequestBody @Valid RequestDTO requestDTO){
    ResponseDTO responseDTO = taskService.createTask(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
}
@GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> retrieveTask(@PathVariable Long id){
    ResponseDTO responseDTO = taskService.getMyTask(id);
    return ResponseEntity.ok(responseDTO);
}
@GetMapping
    public ResponseEntity<Page<ResponseDTO>> retrieveAllTask(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {

    Page<ResponseDTO> responseDTOS = taskService.getAllTasks(page, size);
    return ResponseEntity.ok(responseDTOS);
}

@GetMapping("/my")
public ResponseEntity<Page<ResponseDTO>> retrieveMyAllTasks(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
    return ResponseEntity.ok(taskService.getAllMyTask(page,size));
}

@GetMapping("/overdue")
public ResponseEntity<List<ResponseDTO>> retrieveMyDueTask(){
        return ResponseEntity.ok(taskService.getOverDueTask());
}

@GetMapping("/status")
public ResponseEntity<Page<ResponseDTO>> retrievetTaskByStatus(@RequestParam Status status ,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(taskService.getTaskByStatus(status , page , size));
}



@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
    taskService.deleteTask(id);
    return ResponseEntity.noContent().build();

}
@PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateTask(@PathVariable Long id , @RequestBody @Valid RequestDTO requestDTO){
    return ResponseEntity.ok().body(taskService.updateTask(id,requestDTO));
}


}


