package com.example.task_management.task.dto;

import com.example.task_management.task.enums.TaskPriority;
import com.example.task_management.task.enums.TaskStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {
    
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
