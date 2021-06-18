package ru.alexkvs.todo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alexkvs.todo.dao.Customer;
import ru.alexkvs.todo.dao.Todo;
import ru.alexkvs.todo.repositories.CustomerRepository;
import ru.alexkvs.todo.repositories.TodoRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/customer")
@Slf4j
public class CustomerRestController {
    private final CustomerRepository customerRepository;
    private final TodoRepository todoRepository;

    public CustomerRestController(CustomerRepository customerRepository, TodoRepository todoRepository) {
        this.customerRepository = customerRepository;
        this.todoRepository = todoRepository;
    }

    @Operation(summary = "Get all customers")
    @GetMapping(produces = {"application/hal+json"})
    public List<Customer> index() {
        log.info("Get all customers");
        return customerRepository.findAll();
    }

    @GetMapping(value = "/{customerId}", produces = {"application/hal+json"})
    @Operation(summary = "Find customer by ID",
            tags = {"customers"},
            description = "Returns a customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The customer",
                            content = {
                                    @Content(
                                            mediaType = "application/hal+json",
                                            schema = @Schema(implementation = Customer.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    public Customer getCustomer(@PathVariable long customerId) {
        log.info("Find customer by ID {}", customerId);

        Customer customer = customerRepository.getById(customerId);
        customer.add(WebMvcLinkBuilder.linkTo(CustomerRestController.class).slash(customerId).withSelfRel());
        Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class)
                .getCustomerTodo(customerId)).withRel("allTasks");
        customer.add(ordersLink);
        return customer;
    }

    @Operation(summary = "Get customer's tasks by its ID")
    @GetMapping(value = "/{customerId}/todo", produces = {"application/hal+json"})
    public CollectionModel<Todo> getCustomerTodo(@PathVariable long customerId) {
        log.info("et customer's tasks by its id {}", customerId);

        List<Todo> todoList = todoRepository.findAllByCustomerIdOrderByCreatedDesc(customerId);
        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(CustomerRestController.class).getCustomer(customerId)).withSelfRel();
        return CollectionModel.of(todoList, link);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}
