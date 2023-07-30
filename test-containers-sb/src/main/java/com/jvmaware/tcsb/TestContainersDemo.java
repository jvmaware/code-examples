package com.jvmaware.tcsb;

import com.jvmaware.tcsb.repositories.StudentRecordRepository;
import com.jvmaware.tcsb.services.StudentRecordsPersistenceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jvmaware.tcsb.repositories")
@EntityScan(basePackages = "com.jvmaware.tcsb.models.physical")
public class TestContainersDemo {

    public static void main(String[] args) {
        SpringApplication.run(TestContainersDemo.class, args);
    }

    @Bean
    public StudentRecordsPersistenceService studentRecordsPersistenceService(StudentRecordRepository studentRecordRepository){
        return new StudentRecordsPersistenceService(studentRecordRepository);
    }

}
