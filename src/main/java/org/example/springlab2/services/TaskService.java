package org.example.springlab2.services;

import org.example.springlab2.dtos.DateDto;
import org.example.springlab2.dtos.PriorityDto;
import org.example.springlab2.dtos.TaskDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

@Service
public class TaskService {
    ArrayList<TaskDto> tasks = new ArrayList<>();

    public void addTask(String title, String description, int priority, Date deadline) {
        PriorityDto priorityDto = new PriorityDto(priority);
        DateDto deadlineDto = new DateDto(deadline);
        TaskDto task = new TaskDto(title, description, deadlineDto, priorityDto);
        tasks.add(task);
    }

    public void removeTask(TaskDto task){
        tasks.remove(task);
    }

    public void updateTask(TaskDto task, String description){
        task.description = description;
    }

    public void markAsDone(TaskDto task){
        task.completed = true;
    }

    public void sortByPriority(){
        ArrayList<TaskDto> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(TaskDto::getPriority));
        tasks = sortedTasks;
    }

    public void sortByDate(){
        ArrayList<TaskDto> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks, Comparator.comparing(TaskDto::getDeadline));
        tasks = sortedTasks;
    }
}
