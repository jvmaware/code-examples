package com.jvmaware.streamingserver.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface EmployeePersistenceService {

    ResponseEntity<StreamingResponseBody> findActiveEmployee();
}
