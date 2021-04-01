package com.jvmaware.streamingserver.model.logical;

import java.time.LocalDate;

public class Employee {

    private final String firstName;
    private final String lastName;
    private final LocalDate hireDate;
    private final LocalDate birthDate;
    private final String gender;

    public Employee(String firstName, String lastName, LocalDate hireDate, LocalDate birthDate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.birthDate = birthDate;
        this.gender = gender;
    }


    public static final class EmployeeBuilder {
        private String firstName;
        private String lastName;
        private LocalDate hireDate;
        private LocalDate birthDate;
        private String gender;

        private EmployeeBuilder() {
        }

        public static EmployeeBuilder anEmployee() {
            return new EmployeeBuilder();
        }

        public EmployeeBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder withHireDate(LocalDate hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public EmployeeBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public EmployeeBuilder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Employee build() {
            return new Employee(firstName, lastName, hireDate, birthDate, gender);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate=" + hireDate +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                '}';
    }
}
