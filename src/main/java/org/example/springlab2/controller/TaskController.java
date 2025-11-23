package org.example.springlab2.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.dto.outbound.TaskDtoOutbound;
import org.example.springlab2.entity.Task;
import org.example.springlab2.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks",
            description = "Returns list of all tasks. Can sort by priority or date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully got a list"),
    })
    @GetMapping
    public List<TaskDtoOutbound> getAll(
            @RequestParam(required = false) String sort
            ) {
        Optional<String> str_sort = Optional.ofNullable(sort);
        return switch (str_sort.orElse("")) {
            case "priority" -> taskService.sortByPriority();
            case "date" -> taskService.sortByDate();
            default -> taskService.findAll();
        };
    }

    @Operation(summary = "Create task",
            description = "Create task with provided JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created task"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDtoInbound inbound) {
        Task new_task = taskService.addTask(inbound);
        return ResponseEntity.status(201).body(new_task);
    }

    @Operation(summary = "Mark task as complete",
    description = "Marks task by id as completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task marked as completed"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> toggleComplete(@PathVariable("id") UUID id) {
        taskService.toggleComplete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete task",
    description = "Delete task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted task"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") UUID id) {
        taskService.removeById(id);
        return ResponseEntity.noContent().build();
    }
}
