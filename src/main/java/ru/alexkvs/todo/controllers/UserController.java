package ru.alexkvs.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexkvs.todo.dao.UserDAO;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserDAO userDao;

    public UserController(UserDAO personDao) {
        this.userDao = personDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userDao.all());
        return "users/index";
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public String getPerson(@PathVariable long id, Model model) {
        model.addAttribute("user", userDao.findById(id));
        return "users/user";
    }
}
