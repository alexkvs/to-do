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

    private final UserDAO personDAO;

    public UserController(UserDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", personDAO.all());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable long id, Model model) {
        model.addAttribute("user", personDAO.findById(id));
        return "users/user";
    }
}
