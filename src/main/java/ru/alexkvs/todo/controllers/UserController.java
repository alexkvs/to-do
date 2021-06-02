package ru.alexkvs.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexkvs.todo.repositories.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @GetMapping(value = "/{id}")
    public String getPerson(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "users/user";
    }
}
