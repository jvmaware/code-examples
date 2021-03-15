package com.jvmaware.streamingserver.model.physical;

import com.jvmaware.streamingserver.model.logical.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Employee")
public class EmployeePhysical {

    @Id
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    public EmployeePhysical(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = true;
    }

    public EmployeePhysical(Employee employee) {
        this(employee.getFirstName(), employee.getLastName());
    }

    public EmployeePhysical() {

    }

    public Employee toModel() {
        return new Employee(this.getFirstName(), this.getLastName());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
