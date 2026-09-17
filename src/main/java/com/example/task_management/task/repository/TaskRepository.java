package com.example.task_management.task.repository;

import com.example.task_management.task.entity.Task;
import com.example.task_management.task.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    List<Task> findByStatus(TaskStatus status);
}
