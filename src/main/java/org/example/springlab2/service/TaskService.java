package org.example.springlab2.service;


import org.example.springlab2.dto.inbound.TaskDtoInbound;
import org.example.springlab2.dto.outbound.TaskDtoOutbound;
import org.example.springlab2.entity.Task;
import org.example.springlab2.exception.TaskNotFoundException;
import org.example.springlab2.mapper.TaskMapper;
import org.example.springlab2.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task addTask(TaskDtoInbound inbound) {
        Task task = taskMapper.toEntity(inbound);
        task.setId(UUID.randomUUID());
        return taskRepository.save(task);
    }

    public void removeById(UUID id){
        taskRepository.deleteById(id);
    }

    public Task updateTaskById(UUID id, TaskDtoInbound inbound){
        if(!taskRepository.existsById(id))
            throw new TaskNotFoundException("Task with id=" + id + "notfound");

        Task task = taskMapper.toEntity(inbound);
        task.setId(id);
        return taskRepository.save(task);
    }

    public List<TaskDtoOutbound> findAll(){
        return taskRepository.findAll().stream().map(taskMapper::toDto).toList();
    }

    public void toggleComplete(UUID id){
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Task with id=" + id + "notfound")
        );
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
