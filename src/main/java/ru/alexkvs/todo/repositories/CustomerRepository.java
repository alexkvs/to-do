package ru.alexkvs.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.alexkvs.todo.dao.Customer;

//@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
