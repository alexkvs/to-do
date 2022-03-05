package ru.alexkvs.todo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alexkvs.todo.dao.Customer;
import ru.alexkvs.todo.repositories.CustomerRepository;

import javax.validation.Valid;

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

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "customers/create";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("customer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/{id}/edit")
    public String editCustomer(@PathVariable long id, Model model) {
        model.addAttribute("customer", customerRepository.findById(id).get());
        return "customers/edit";
    }

    @PatchMapping("/{id}/update")
    public String update(@Valid @ModelAttribute("customer") Customer customer, @PathVariable long id) {
        customerRepository.findById(id).ifPresent(
                found -> {
                    found.setFirstName(customer.getFirstName());
                    found.setLastName(customer.getLastName());
                    found.setEmail(customer.getEmail());
                    customerRepository.save(found);
                }
        );
        return "redirect:/customer";
    }

    /**
     * Можно использовать для заполнения общих атрибутов модели, используемых во всех методах
     */
    /*@ModelAttribute
    public void populateAllCustomersModel(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
    }

    @ModelAttribute
    public void populateCustomerModel(@PathVariable long id, Model model) {
        model.addAttribute("customer", customerRepository.findById(id).get());
    }*/
}
