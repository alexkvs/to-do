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
import ru.alexkvs.todo.dao.UserDAO;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserRestController {
    private final UserDAO userDao;
    private final TodoDao todoDao;

    public UserRestController(UserDAO personDao, TodoDao todoDao) {
        this.userDao = personDao;
        this.todoDao = todoDao;
    }

    @GetMapping()
    public Collection<User> index() {
        return userDao.all();
    }

    @GetMapping(value = "/{userId}", produces = {"application/hal+json"})
    public User getUser(@PathVariable long userId) {
        User user = userDao.findById(userId);
        user.add(WebMvcLinkBuilder.linkTo(UserRestController.class).slash(userId).withSelfRel());
        List<Todo> todoList = todoDao.getAllTasksForUser(userId);
        if (!todoList.isEmpty()) {
            Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class)
                    .getTodosForUser(userId)).withRel("allOrders");
            user.add(ordersLink);
        }
        return user;
    }

    @GetMapping(value = "/{userId}/todo", produces = {"application/hal+json"})
    public CollectionModel<Todo> getTodosForUser(@PathVariable long userId) {
        List<Todo> todoList = todoDao.getAllTasksForUser(userId);
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UserRestController.class).getUser(userId)).withSelfRel();
        return CollectionModel.of(todoList, link);
    }
}
