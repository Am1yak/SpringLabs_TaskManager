package org.example.springlab2.controller;


import jakarta.validation.Valid;
import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "index.html";
    }

    @PostMapping
    public String createTask(@Valid TaskDtoInbound inbound) {
        taskService.addTask(inbound);
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String toggleComplete(@PathVariable("id") UUID id) {
        taskService.toggleComplete(id);
        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") UUID id) {
        taskService.removeById(id);
        return "redirect:/tasks";
    }

}
