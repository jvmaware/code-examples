package com.jvmaware.streamingserver.controllers;

import com.google.gson.Gson;
import com.jvmaware.streamingserver.model.logical.Employee;
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.stream.Stream;

/**
 * an endpoint to stream json response via StreamingResponseBody
 * @author gaurs
 */
@RestController
@RequestMapping(value = "/stream")
public class StreamingDataEndPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final EmployeePersistenceService employeePersistenceService;
    private final Gson gson = new Gson();

    public StreamingDataEndPoint(EmployeePersistenceService employeePersistenceService) {
        this.employeePersistenceService = employeePersistenceService;
    }

    /**
     * The method returns a streaming response body to the client in a separate
     * thread as defined by the {@link com.jvmaware.streamingserver.config.AsyncConfig}
     * set the content type for it to work in browser
     *
     * @return streaming response
     */
    @GetMapping(value = "/employee")
    public ResponseEntity<StreamingResponseBody> findAllEmployee() {
        logger.info("request received to fetch all employee details");
        Stream<Employee> employees = employeePersistenceService.findActiveEmployee();

        StreamingResponseBody responseBody = httpResponseOutputStream -> {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(httpResponseOutputStream))) {
                employees.forEach(employee -> {
                    try {
                        writer.write(gson.toJson(employee));
                        writer.flush();
                    } catch (IOException exception) {
                        logger.error("exception occurred while writing object to stream", exception);
                    }
                });
            } catch (Exception exception) {
                logger.error("exception occurred while publishing data", exception);
            }
            logger.info("finished streaming records");
        };

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseBody);
    }

}
