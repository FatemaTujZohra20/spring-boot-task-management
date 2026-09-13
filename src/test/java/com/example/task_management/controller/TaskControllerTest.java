package com.example.task_management.controller;

import com.example.task_management.dto.TaskResponse;
import com.example.task_management.enums.TaskPriority;
import com.example.task_management.enums.TaskStatus;
import com.example.task_management.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
 
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockitoBean
    private TaskService taskService;
    
    @Test
    void shouldCreateTask() throws Exception {
        
        TaskResponse response = TaskResponse.builder()
                .id(1L)
                .title("Learn Spring Boot")
                .description("Practice REST API")
                .status(TaskStatus.TODO)
                .priority(TaskPriority.HIGH)
                .build();
        
        when(taskService.createTask(any()))
                .thenReturn(response);
        
        String request = """
                {
                    "title": "Learn Spring Boot",
                    "description": "Practice REST API",
                    "status": "TODO",
                    "priority": "HIGH"
                }
                """;
        
        mockMvc.perform(
                post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                                .value("Learn Spring Boot"));
        
    }
    
}
