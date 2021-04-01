package com.jvmaware.streamingserver.services;

import com.jvmaware.streamingserver.model.logical.Employee;
import com.jvmaware.streamingserver.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.stream.Stream;

@Service
public class EmployeePersistenceServiceImpl implements EmployeePersistenceService {

    private final EmployeeRepository employeeRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    public EmployeePersistenceServiceImpl(EmployeeRepository employeeRepository, DataSource dataSource) {
        this.employeeRepository = employeeRepository;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Stream<Employee> findActiveEmployee() {
        jdbcTemplate.setFetchSize(10);
        return jdbcTemplate.queryForStream("Select FIRST_NAME, LAST_NAME, HIRE_DATE, BIRTH_DATE, GENDER from employees",
                (resultSet, rowNum) ->
                        new Employee(resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"),
                                resultSet.getDate("HIRE_DATE").toLocalDate(),
                                resultSet.getDate("BIRTH_DATE").toLocalDate(), resultSet.getString("GENDER"))
        );
    }

    /*
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<StreamingResponseBody> findActiveEmployee() {
        Stream<Employee> employees = employeeRepository.getAllEmployees().map(EmployeePhysical::toModel);
        StreamingResponseBody responseBody = httpResponseOutputStream -> {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(httpResponseOutputStream))) {
                employees.forEach(employee -> {
                    try {
                        writer.write(gson.toJson(employee));
                        logger.info("streamed record");
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
    */
}
