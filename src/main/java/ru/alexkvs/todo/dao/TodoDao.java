package ru.alexkvs.todo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TodoDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<Todo> all() {
        return jdbcTemplate.query("SELECT * FROM todo", new BeanPropertyRowMapper<>(Todo.class));
    }

    public List<Todo> getCustomerAllTasks(long id) {
        return jdbcTemplate.query("SELECT * FROM todo WHERE customer_id=?",
                new BeanPropertyRowMapper<>(Todo.class),
                id);
    }
}
