package org.example.springlab2.repository;

import org.example.springlab2.entity.Task;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name = "application.jdbc.repository.type", havingValue = "template")
public class TaskJdbcTemplateRepository implements TaskRepository{

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TaskJdbcTemplateRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Task save(Task task) {;
        String sqlInsertQuery = "INSERT INTO tasks (id, title, description, completed, deadline, priority)" +
                                "VALUES (:id, :title, :description, :completed, :deadline, :priority)";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", task.getId())
                .addValue("title", task.getTitle())
                .addValue("description", task.getDescription())
                .addValue("completed", task.isCompleted())
                .addValue("deadline", Date.valueOf(task.getDeadline()))
                .addValue("priority", task.getPriority());

        int update = namedParameterJdbcTemplate.update(sqlInsertQuery, parameters);
        return task;
    }

    @Override
    public List<Task> findAll() {
        String sqlSelectAllQuery = "SELECT * FROM tasks";

        return jdbcTemplate.query(sqlSelectAllQuery, (resultSet, amount) -> new Task(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getBoolean("completed"),
                resultSet.getObject("deadline", Date.class).toLocalDate(),
                resultSet.getInt("priority")
        ));
    }

    @Override
    public List<Task> findAllByPriority(int priority) {
        String sqlSelectAllQuery = "SELECT * FROM tasks WHERE priority = :priority";

        Map<String, Integer> params = Map.of("priority", priority);

        return namedParameterJdbcTemplate.query(sqlSelectAllQuery, params, (resultSet, amount) -> new Task(
                resultSet.getObject("id", UUID.class),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getBoolean("completed"),
                resultSet.getObject("deadline", Date.class).toLocalDate(),
                resultSet.getInt("priority")
        ));
    }

    @Override
    public Optional<Task> findById(UUID id) {
        String sqlSelectByIdQuery = "SELECT * FROM tasks WHERE id = :id";

        Map<String, UUID> parameters = Map.of("id", id);
        Task task;

        try {
             task = namedParameterJdbcTemplate.queryForObject(sqlSelectByIdQuery, parameters, (resultSet, amount) -> new Task(
                    resultSet.getObject("id", UUID.class),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("completed"),
                    resultSet.getObject("deadline", Date.class).toLocalDate(),
                    resultSet.getInt("priority")
            ));
        } catch (EmptyResultDataAccessException ex){
            task = null;
        }

        return Optional.ofNullable(task);
    }

    @Override
    public Optional<Task> findByTitle(String title) {
        String sqlSelectByIdQuery = "SELECT * FROM tasks WHERE title = :title";

        Map<String, String> parameters = Map.of("id", title);
        Task task;

        try {
            task = namedParameterJdbcTemplate.queryForObject(sqlSelectByIdQuery, parameters, (resultSet, amount) -> new Task(
                    resultSet.getObject("id", UUID.class),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getBoolean("completed"),
                    resultSet.getObject("deadline", Date.class).toLocalDate(),
                    resultSet.getInt("priority")
            ));
        } catch (EmptyResultDataAccessException ex){
            task = null;
        }

        return Optional.ofNullable(task);
    }

    @Override
    public void deleteById(UUID id) {
        String sqlDeleteQuery = "DELETE FROM tasks WHERE id = :id";
        Map<String, UUID> parameters = Map.of("id", id);
        namedParameterJdbcTemplate.update(sqlDeleteQuery, parameters);
    }

    @Override
    public boolean existsById(UUID id) {
        String sqlExistsQuery = "SELECT EXISTS(SELECT 1 FROM tasks WHERE id = :id)";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sqlExistsQuery, params, Boolean.class));
    }

    @Override
    public boolean existsByTitle(String title) {
        String sqlExistsQuery = "SELECT EXISTS(SELECT 1 FROM tasks WHERE title = :title)";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("title", title);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(sqlExistsQuery, params, Boolean.class));
    }
}
