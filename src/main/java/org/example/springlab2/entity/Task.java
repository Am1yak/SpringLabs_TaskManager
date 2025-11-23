package org.example.springlab2.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate deadline;
    private int priority;


    public Task(LocalDate deadline, String description, int priority, String title) {
        this.deadline = deadline;
        this.description = description;
        this.priority = priority;
        this.title = title;
        this.completed = false;
    }
}
