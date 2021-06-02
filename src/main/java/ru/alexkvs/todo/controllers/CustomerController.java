package ru.alexkvs.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexkvs.todo.repositories.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers/index";
    }

    @GetMapping(value = "/{id}")
    public String getPerson(@PathVariable long id, Model model) {
        model.addAttribute("customer", customerRepository.findById(id).get());
        return "customers/customer";
    }
}
