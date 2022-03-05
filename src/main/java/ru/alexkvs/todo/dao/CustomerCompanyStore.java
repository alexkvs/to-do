package ru.alexkvs.todo.dao;

import javax.persistence.*;

@Entity
@Table(name = "customer_company_store")
public class CustomerCompanyStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "company_id")
    private Long companyId;
}
