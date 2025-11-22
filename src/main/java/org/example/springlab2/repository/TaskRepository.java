package org.example.springlab2.repository;

import org.example.springlab2.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findAll();
    List<Task> findAllByPriority(int priority);
    Optional<Task> findById(UUID id);
    Optional<Task> findByTitle(String title);
    void deleteById(UUID id);
    boolean existsById(UUID id);
    boolean existsByTitle(String title);
}
