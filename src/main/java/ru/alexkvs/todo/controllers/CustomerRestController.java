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
import ru.alexkvs.todo.dao.Customer;
import ru.alexkvs.todo.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/customer")
public class CustomerRestController {
    private final CustomerRepository customerRepository;
    private final TodoDao todoDao;

    public CustomerRestController(CustomerRepository customerRepository, TodoDao todoDao) {
        this.customerRepository = customerRepository;
        this.todoDao = todoDao;
    }

    @GetMapping(produces = {"application/hal+json"})
    public Iterable<Customer> index() {
        return customerRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public Customer getCustomer(@PathVariable long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent((u) -> {
            u.add(WebMvcLinkBuilder.linkTo(CustomerRestController.class).slash(id).withSelfRel());
            List<Todo> todoList = todoDao.getCustomerAllTasks(id);
            if (!todoList.isEmpty()) {
                Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                        .getCustomerTodo(id)).withRel("allOrders");
                u.add(ordersLink);
            }
        });
        return customer.get();
    }

    @GetMapping(value = "/{id}/todo", produces = {"application/hal+json"})
    public CollectionModel<Todo> getCustomerTodo(@PathVariable long id) {
        List<Todo> todoList = todoDao.getCustomerAllTasks(id);
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class).getCustomer(id)).withSelfRel();
        return CollectionModel.of(todoList, link);
    }
}
