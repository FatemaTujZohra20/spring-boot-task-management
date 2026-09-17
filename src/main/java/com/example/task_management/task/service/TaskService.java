package com.example.task_management.task.service;

import com.example.task_management.task.dto.TaskRequest;
import com.example.task_management.task.dto.TaskResponse;
import com.example.task_management.task.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    
    TaskResponse createTask(TaskRequest request);
    
    List<TaskResponse> getAllTasks();
    
    TaskResponse getTaskById(Long id);
    
    TaskResponse updateTask(Long id, TaskRequest request);
    
    void deleteTask(Long id);
    
    List<TaskResponse> getTasksByUserStatus(TaskStatus status);
}
