package org.example.springlab2.repository;

import org.example.springlab2.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(UUID id);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
