package ru.alexkvs.todo.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alexkvs.todo.dao.Customer;
import ru.alexkvs.todo.dao.Todo;
import ru.alexkvs.todo.repositories.CustomerRepository;
import ru.alexkvs.todo.repositories.TodoRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController {
    private final CustomerRepository customerRepository;
    private final TodoRepository todoRepository;

    public CustomerRestController(CustomerRepository customerRepository, TodoRepository todoRepository) {
        this.customerRepository = customerRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping(produces = {"application/hal+json"})
    public Iterable<Customer> index() {
        return customerRepository.findAll();
    }

    @GetMapping(value = "/{customerId}", produces = {"application/hal+json"})
    public Customer getCustomer(@PathVariable long customerId) {
        Customer customer = customerRepository.getById(customerId);
        customer.add(WebMvcLinkBuilder.linkTo(CustomerRestController.class).slash(customerId).withSelfRel());
        Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                .getCustomerTodo(customerId)).withRel("allTasks");
        customer.add(ordersLink);
        return customer;
    }

    @GetMapping(value = "/{customerId}/todo", produces = {"application/hal+json"})
    public CollectionModel<Todo> getCustomerTodo(@PathVariable long customerId) {
        List<Todo> todoList = todoRepository.findAllByCustomerIdOrderByCreatedDesc(customerId);
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class).getCustomer(customerId)).withSelfRel();
        return CollectionModel.of(todoList, link);
    }
}
