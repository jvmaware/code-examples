package com.jvmaware.streamingserver.services;

import com.jvmaware.streamingserver.model.logical.Employee;

import java.util.stream.Stream;

public interface EmployeePersistenceService {

    Stream<Employee> findActiveEmployee();
    //ResponseEntity<StreamingResponseBody> findActiveEmployee();
}
