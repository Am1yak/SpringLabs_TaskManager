package org.example.springlab2.entity;

import lombok.*;

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
    private Date deadline;
    private int priority;
}
