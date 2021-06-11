package ru.alexkvs.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexkvs.todo.dao.Customer;

//@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
