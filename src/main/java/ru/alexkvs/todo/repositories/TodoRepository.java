package ru.alexkvs.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkvs.todo.dao.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByCustomerIdOrderByCreatedDesc(Long customerId);
}
