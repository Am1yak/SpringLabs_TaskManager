package org.example.springlab2.dtos;

import java.util.Date;

public class TaskDto {
    public String title;
    public String description;
    public boolean completed;
    public DateDto deadline;
    public PriorityDto priority;
    
    public TaskDto(String title,
                   String description,
                   DateDto deadline,
                   PriorityDto priority) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.deadline = deadline;
        this.priority = priority;
    }

    public int getPriority() {
        return priority.getPriority();
    }

    public Date getDeadline() {
        return deadline.getDate();
    }
}
