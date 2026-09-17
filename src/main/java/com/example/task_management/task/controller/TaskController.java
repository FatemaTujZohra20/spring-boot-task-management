package com.example.task_management.task.controller;

import com.example.task_management.task.dto.TaskRequest;
import com.example.task_management.task.dto.TaskResponse;
import com.example.task_management.task.enums.TaskStatus;
import com.example.task_management.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(
            @Valid @RequestBody TaskRequest request){
        return taskService.createTask(request);
    }
    
    @GetMapping
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }
    
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }
    
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request){
        return taskService.updateTask(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
            @PathVariable Long id){
        taskService.deleteTask(id);
    }
    
    @GetMapping("/status/{status}")
    public List<TaskResponse> getTasksByStatus(
            @PathVariable TaskStatus status){
        return taskService.getTasksByUserStatus(status);
    }
}
