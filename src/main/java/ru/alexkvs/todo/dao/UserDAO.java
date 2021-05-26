package ru.alexkvs.todo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDAO {

    private static long NEXT_ID = 0;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<User> all() {
        return jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<>(User.class));
    }

    public void add(User user) {
        user.setId(++NEXT_ID);
        jdbcTemplate.update("INSERT INTO users VALUES(?, ?, ?, ?, ?)",
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getLastName());
    }

    public User findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?",
                new BeanPropertyRowMapper<>(User.class),
                id);
    }
}
