package org.example.springlab2.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.dto.outbound.TaskDtoOutbound;
import org.example.springlab2.entity.Task;
import org.example.springlab2.service.TaskService;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "Get all tasks or filter by priority",
            description = "Returns list of all tasks. Optionally filter by priority")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned list of tasks",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TaskDtoOutbound.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<TaskDtoOutbound>> getAll(
            @RequestParam(required = false) Integer priority
    ) {
        if (priority == null) {
            return ResponseEntity.ok(taskService.findAll());
        }
        return ResponseEntity.ok(taskService.findAllByPriority(priority));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id",
            description = "Returns task identified by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned task",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDtoOutbound.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public ResponseEntity<TaskDtoOutbound> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Get task by title",
            description = "Returns task identified by title")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully returned task",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskDtoOutbound.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public ResponseEntity<TaskDtoOutbound> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(taskService.findByTitle(title));
    }

    @PostMapping
    @Operation(summary = "Create new task",
            description = "Creates new task with provided JSON")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Task.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDtoInbound inbound) {
        Task newTask = taskService.addTask(inbound);
        URI location = URI.create("/api/v1/tasks/" + newTask.getId());
        return ResponseEntity.created(location).body(newTask);
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Mark task as complete",
            description = "Marks task by id as completed")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task marked as completed. No content"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public ResponseEntity<Void> toggleComplete(@PathVariable UUID id) {
        taskService.toggleComplete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task",
            description = "Deletes task by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task deleted successfully. No content"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
