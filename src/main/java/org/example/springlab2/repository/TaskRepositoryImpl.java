package org.example.springlab2.repository;

import org.example.springlab2.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final Map<UUID, Task> taskStorage = new HashMap<>();

    @Override
    public Task save(Task task) {
        taskStorage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return taskStorage.values().stream().toList();
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return  Optional.of(taskStorage.get(id));
    }

    @Override
    public void deleteById(UUID id) {
        taskStorage.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return taskStorage.containsKey(id);
    }
}
