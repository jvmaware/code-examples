package com.jvmaware.streamingserver.services;

import com.google.gson.Gson;
import com.jvmaware.streamingserver.model.physical.EmployeePhysical;
import com.jvmaware.streamingserver.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.transaction.Transactional;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

@Service
public class EmployeePersistenceServiceImpl implements EmployeePersistenceService {

    private final EmployeeRepository employeeRepository;
    private final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public EmployeePersistenceServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public StreamingResponseBody findActiveEmployee() {
        List<EmployeePhysical> employees = employeeRepository.findAll();
        return httpResponseOutputStream -> {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(httpResponseOutputStream))) {
                employees.forEach(employee -> {
                    try {
                        writer.write(gson.toJson(employee));
                        Thread.sleep(1000); // to demo
                        logger.info("streamed record");
                        writer.flush();
                    } catch (IOException | InterruptedException exception) {
                        logger.error("exception occurred while writing object to stream", exception);
                    }
                });
            } catch (Exception exception) {
                logger.error("exception occurred while publishing data", exception);
            }
            logger.info("finished streaming records");
        };
    }
}
