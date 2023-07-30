package com.jvmaware.tcsb.services;

import com.jvmaware.tcsb.models.logical.StudentRecordLogical;
import com.jvmaware.tcsb.models.physical.StudentRecordPhysical;
import com.jvmaware.tcsb.repositories.StudentRecordRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class StudentRecordsPersistenceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StudentRecordRepository studentRecordRepository;

    public StudentRecordsPersistenceService(StudentRecordRepository studentRecordRepository) {
        this.studentRecordRepository = studentRecordRepository;
    }


    /**
     * The method is used to persist a student record in the DB.
     * It's an upsert operation based on the presence of id
     * attribute.
     *
     * @param studentRecordLogical to be persisted
     * @return persisted record
     * @throws NullPointerException if param is null
     */
    public StudentRecordLogical save(@Nonnull StudentRecordLogical studentRecordLogical){
        logger.info("applying logical to physical model transformation on : [{}]", studentRecordLogical);
        var physical = studentRecordLogical.toPhysical();
        var saved = studentRecordRepository.save(physical);
        return saved.toLogical();
    }

    /**
     * Fetch student for a given id
     * @param id unique identifier
     * @return student record if available, null otherwise
     */
    public @Nullable StudentRecordLogical get(Long id){
        Optional<StudentRecordPhysical> record = studentRecordRepository.findById(id);
        if(record.isPresent()){
            logger.info("found 1 record for id: [{}]", id);
            return record.get().toLogical();
        }else {
            logger.info("no record found for the id: [{}]", id);
            return null;
        }
    }

    /**
     * fetch all student records present in the system
     * @return a list of student records if available
     *         a blank list otherwise
     */
    public List<StudentRecordLogical> findAll(){
        logger.info("request received to fetch all the student records in the DB");
        var students = studentRecordRepository.findAll().stream().map(StudentRecordPhysical::toLogical).toList();

        logger.info("successfully found records: [{}]", students.size());
        return students;
    }
}
