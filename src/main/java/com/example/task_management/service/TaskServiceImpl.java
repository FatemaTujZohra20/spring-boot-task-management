package com.example.task_management.service;

import com.example.task_management.dto.TaskRequest;
import com.example.task_management.dto.TaskResponse;
import com.example.task_management.entity.Task;
import com.example.task_management.enums.TaskStatus;
import com.example.task_management.exception.TaskNotFoundException;
import com.example.task_management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    
    @Override
    public TaskResponse createTask(TaskRequest request){
        
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .build();
        
        Task savedTask = taskRepository.save(task);
        
        return mapToResponse(savedTask);
    }
    
    @Override
    public List<TaskResponse> getAllTasks(){
        
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    @Override
    public TaskResponse getTaskById(Long id){
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        
        return mapToResponse(task);
    }
    
    @Override
    public TaskResponse updateTask(Long id, TaskRequest request){
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        
        Task updateTask = taskRepository.save(task);
        
        return mapToResponse(updateTask);
    }
    
    @Override
    public void deleteTask(Long id){
        
        if(!taskRepository.existsById(id)){
            throw new TaskNotFoundException(id);
        }
        
        taskRepository.deleteById(id);
    }
    
    @Override
    public List<TaskResponse> getTasksByUserStatus(TaskStatus status){
        
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    private TaskResponse mapToResponse(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

}
