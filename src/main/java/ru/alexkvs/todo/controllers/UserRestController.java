package ru.alexkvs.todo.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexkvs.todo.dao.Todo;
import ru.alexkvs.todo.dao.TodoDao;
import ru.alexkvs.todo.dao.User;
import ru.alexkvs.todo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserRepository userRepository;
    private final TodoDao todoDao;

    public UserRestController(UserRepository userRepository, TodoDao todoDao) {
        this.userRepository = userRepository;
        this.todoDao = todoDao;
    }

    @GetMapping(produces = {"application/hal+json"})
    public Iterable<User> index() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{userId}", produces = {"application/hal+json"})
    public User getUser(@PathVariable long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent((u) -> {
            u.add(WebMvcLinkBuilder.linkTo(UserRestController.class).slash(userId).withSelfRel());
            List<Todo> todoList = todoDao.getAllTasksForUser(userId);
            if (!todoList.isEmpty()) {
                Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class)
                        .getTodosForUser(userId)).withRel("allOrders");
                u.add(ordersLink);
            }
        });
        return user.get();
    }

    @GetMapping(value = "/{userId}/todo", produces = {"application/hal+json"})
    public CollectionModel<Todo> getTodosForUser(@PathVariable long userId) {
        List<Todo> todoList = todoDao.getAllTasksForUser(userId);
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).getUser(userId)).withSelfRel();
        return CollectionModel.of(todoList, link);
    }
}
