package com.example.task_management.task.service;

import com.example.task_management.task.dto.TaskRequest;
import com.example.task_management.task.dto.TaskResponse;
import com.example.task_management.task.entity.Task;
import com.example.task_management.task.enums.TaskPriority;
import com.example.task_management.task.enums.TaskStatus;
import com.example.task_management.task.exception.TaskNotFoundException;
import com.example.task_management.task.repository.TaskRepository;
import com.example.task_management.task.service.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskServiceImpl taskService;
    
    @Test
    void shouldCreateTask() {
        
        TaskRequest request = new TaskRequest();
        
        request.setTitle("Learn Spring Boot");
        request.setDescription("Learn Spring Boot");
        request.setStatus(TaskStatus.TODO);
        request.setPriority(TaskPriority.HIGH);
        
        Task savedTask = Task.builder()
                .id(1L)
                .title("Learn Spring Boot")
                .description("Practice REST API")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.HIGH)
                .build();
        
        when(taskRepository.save(any(Task.class)))
                .thenReturn(savedTask);
        
        TaskResponse response = taskService.createTask(request);
        
        assertEquals(1L, response.getId());
        assertEquals("Learn Spring Boot", response.getTitle());
        assertEquals(TaskStatus.TODO, response.getStatus());
        
        verify(taskRepository).save(any(Task.class));
    }
    
    @Test
    void shouldThrowExceptionWhenTaskDoesNotExist(){
        
        when(taskRepository.findById(999L))
                .thenReturn(Optional.empty());
        
        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.getTaskById(999L)
        );
        
        verify(taskRepository).findById(999L);
    }

}
