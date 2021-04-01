package com.jvmaware.streamingserver.model.physical;

import com.jvmaware.streamingserver.model.logical.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "Employees")
public class EmployeePhysical {

    @Id
    @Column(name = "EMP_NO")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "HIRE_DATE")
    private LocalDate hireDate;

    public EmployeePhysical() {
    }

    public Employee toModel() {
        return Employee.EmployeeBuilder
                .anEmployee()
                .withFirstName(this.firstName)
                .withLastName(this.lastName)
                .withGender(this.gender)
                .withBirthDate(this.birthDate)
                .withHireDate(hireDate)
                .build();
    }
}
