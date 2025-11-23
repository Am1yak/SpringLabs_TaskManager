package org.example.springlab2.service;


import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.dto.outbound.TaskDtoOutbound;
import org.example.springlab2.entity.Task;
import org.example.springlab2.exception.TaskAlreadyExistsException;
import org.example.springlab2.exception.TaskNotFoundException;
import org.example.springlab2.mapper.TaskMapper;
import org.example.springlab2.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public Task addTask(TaskDtoInbound inbound) {
        if (taskRepository.existsByTitle(inbound.title()))
            throw new TaskAlreadyExistsException("Task with this title is already exists");

        Task task = taskMapper.toEntity(inbound);
        return taskRepository.save(task);
    }

    public void removeById(UUID id){
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task updateTaskById(UUID id, TaskDtoInbound inbound){
        if(!taskRepository.existsById(id))
            throw new TaskNotFoundException("Task with id=" + id + " not found");

        Task task = taskMapper.toEntity(inbound);
        task.setId(id);
        return taskRepository.save(task);
    }

    public List<TaskDtoOutbound> findAll(){
        return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
    }

    public List<TaskDtoOutbound> findAllByPriority(int priority){
        if (priority < 1)
            throw new IllegalArgumentException("Priority starts with 1");

        return taskRepository.findAllByPriority(priority).stream().map(taskMapper::toDto).toList();
    }

    public TaskDtoOutbound findById(UUID id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=" + id + " not found"));

        return taskMapper.toDto(task);
    }

    public TaskDtoOutbound findByTitle(String title){
        Task task = taskRepository.findByTitle(title)
                .orElseThrow(() -> new TaskNotFoundException("Task with title: '" + title + "' not found"));

        return taskMapper.toDto(task);
    }

    @Transactional
    public void toggleComplete(UUID id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=" + id + "notfound"));

        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public List<TaskDtoOutbound> sortByPriority(){
        List<Task> sortedTasks = new ArrayList<>(taskRepository.findAll());
        sortedTasks.sort(Comparator.comparing(Task::getPriority));
        return sortedTasks.stream().map(taskMapper::toDto).toList();
    }

    public List<TaskDtoOutbound> sortByDate(){
        List<Task> sortedTasks = new ArrayList<>(taskRepository.findAll());
        sortedTasks.sort(Comparator.comparing(Task::getDeadline));
        return sortedTasks.stream().map(taskMapper::toDto).toList();
    }
}
