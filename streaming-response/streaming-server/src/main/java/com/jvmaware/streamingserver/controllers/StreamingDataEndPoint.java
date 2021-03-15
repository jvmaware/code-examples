package com.jvmaware.streamingserver.controllers;

import com.jvmaware.streamingserver.services.EmployeePersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * an endpoint to stream json response via StreamingResponseBody
 * @author gaurs
 */
@RestController
@RequestMapping(value = "/stream")
public class StreamingDataEndPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final EmployeePersistenceService employeePersistenceService;

    public StreamingDataEndPoint(EmployeePersistenceService employeePersistenceService) {
        this.employeePersistenceService = employeePersistenceService;
    }

    /**
     * The method returns a streaming response body to the client in a seperate
     * thread as defined by the {@link com.jvmaware.streamingserver.config.AsyncConfig}
     * set the content type for it to work in browser
     *
     * @return streaming response
     */
    @GetMapping(value = "/employee")
    public ResponseEntity<StreamingResponseBody> findAllEmployee() {
        logger.info("request received to fetch all employee details");
        StreamingResponseBody activeEmployee = employeePersistenceService.findActiveEmployee();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(activeEmployee);


    }

}
