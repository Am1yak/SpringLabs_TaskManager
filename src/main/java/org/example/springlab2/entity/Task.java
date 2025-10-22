package org.example.springlab2.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate deadline;
    private int priority;

    public Task(LocalDate deadline, String description, int priority, String title) {
        this.deadline = deadline;
        this.description = description;
        this.priority = 1;
        this.title = title;
        this.completed = false;
    }
}
