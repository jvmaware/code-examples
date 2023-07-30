package com.jvmaware.tcsb.endpoints;

import com.jvmaware.tcsb.models.logical.StudentRecordLogical;
import com.jvmaware.tcsb.services.StudentRecordsPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentRecordsEndpoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StudentRecordsPersistenceService studentRecordsPersistenceService;

    public StudentRecordsEndpoint(StudentRecordsPersistenceService studentRecordsPersistenceService) {
        this.studentRecordsPersistenceService = studentRecordsPersistenceService;
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentRecordLogical> save(@RequestBody StudentRecordLogical studentRecordLogical){
        try {
            logger.info("request received to save a new record: [{}]", studentRecordLogical);
            var saved = studentRecordsPersistenceService.save(studentRecordLogical);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception exception){
            logger.error("oops, exception occurred while processing request to save student record: [{}]", studentRecordLogical, exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(studentRecordLogical);
        }
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentRecordLogical>> getAll(){
        try {
            logger.info("request received to fetch all student records");
            var saved = studentRecordsPersistenceService.findAll();

            return ResponseEntity.status(HttpStatus.OK).body(saved);
        }catch (Exception exception){
            logger.error("oops, exception occurred while processing request to fetch all students", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentRecordLogical> get(@PathVariable Long id){
        try {
            logger.info("request received to fetch student with id: [{}]", id);
            var saved = studentRecordsPersistenceService.get(id);

            return ResponseEntity.status(HttpStatus.OK).body(saved);
        }catch (Exception exception){
            logger.error("oops, exception occurred while processing request to fetch student with id: [{}]", id, exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
