package com.jvmaware.streamingserver.services;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface EmployeePersistenceService {

    StreamingResponseBody findActiveEmployee();
}
