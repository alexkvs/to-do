package ru.alexkvs.todo.dao;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id")
public class Customer extends RepresentationModel<Customer> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Todo> tasks = new HashSet<>();

    public Customer() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonManagedReference
    public Set<Todo> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Todo> tasks) {
        this.tasks = tasks;
    }
}
