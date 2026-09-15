package com.example.task_management.service;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    
    TaskResponse createTask(TaskRequest request);
    
    List<TaskResponse> getAllTasks();
    
    TaskResponse getTaskById(Long id);
    
    TaskResponse updateTask(Long id, TaskRequest request);
    
    void deleteTask(Long id);
    
    List<TaskResponse> getTasksByUserStatus(TaskStatus status);
}
