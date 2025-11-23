package org.example.springlab2.repository;

import org.example.springlab2.entity.Task;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "application.jdbc.repository.type", havingValue = "client")
public class TaskJdbcClientRepository implements TaskRepository{

    private final JdbcClient jdbcClient;

    public TaskJdbcClientRepository(JdbcClient jdbcClient) {
        System.out.println(
                "========================================== JDBC Client INIT =========================================="
        );
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            UUID id = UUID.randomUUID();

            jdbcClient.sql("""
                INSERT INTO tasks (id, title, description, completed, deadline, priority)
                VALUES (:id, :title, :description, :completed, :deadline, :priority)
            """)
                    .param("id", id)
                    .param("title", task.getTitle())
                    .param("description", task.getDescription())
                    .param("completed", task.isCompleted())
                    .param("deadline", Date.valueOf(task.getDeadline()))
                    .param("priority", task.getTitle())
                    .update();

            return new Task(id, task.getTitle(), task.getDescription(), task.isCompleted(),
                    task.getDeadline(), task.getPriority());
        }

        jdbcClient.sql("""
            UPDATE tasks
            SET title = :title,
                description = :description,
                completed = :completed,
                deadline = :deadline,
                priority = :priority
            WHERE id = :id
        """)    .param("id", task.getId())
                .param("title", task.getTitle())
                .param("description", task.getDescription())
                .param("completed", task.isCompleted())
                .param("deadline", Date.valueOf(task.getDeadline()))
                .param("priority", task.getPriority())
                .update();

        return task;
    }

    @Override
    public List<Task> findAll() {
        return jdbcClient.sql("SELECT * FROM tasks").query(Task.class).list();
    }

    @Override
    public List<Task> findAllByPriority(int priority) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE priority = :priority")
                .param("priority", priority)
                .query(Task.class)
                .list();
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE id = :id")
                .param("id", id)
                .query(Task.class)
                .optional();
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        return jdbcClient.sql("SELECT * FROM tasks WHERE title = :title")
                .param("title", title)
                .query(Task.class)
                .optional();
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient.sql("DELETE FROM tasks WHERE id = :id").param("id", id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM tasks WHERE id = :id)")
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public boolean existsByTitle(String title) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM tasks WHERE title = :title)")
                .param("title", title)
                .query(Boolean.class)
                .single();
    }
}
