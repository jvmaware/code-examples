package com.jvmaware.tcsb.itc.services

import com.jvmaware.tcsb.models.logical.AddressRecordLogical
import com.jvmaware.tcsb.models.logical.StudentRecordLogical
import com.jvmaware.tcsb.repositories.StudentRecordRepository
import com.jvmaware.tcsb.services.StudentRecordsPersistenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@SpringBootTest
@Testcontainers
class StudentRecordsPersistenceServiceSpec extends Specification{

    @Autowired
    StudentRecordsPersistenceService studentRecordsPersistenceService

    @ServiceConnection
    static MariaDBContainer mariaDBContainer = new MariaDBContainer<>("mariadb:10.6.13")

    def setupSpec(){
        mariaDBContainer.start()
    }

    def "test saving a new record"(){
        given: "application context is created and all beans are available"
        and: "a student record to be persisted"
        def student = new StudentRecordLogical(null, "jvm", "aware",
                      new AddressRecordLogical(null, 101, "jvmlane", "jvmverse"))

        when: "the record is saved"
        def savedStudent = studentRecordsPersistenceService.save(student)

        then: "the record should be correctly persisted and an id is assigned to it"
        null != savedStudent && null != savedStudent.id() && 1L == savedStudent.id()
        and: "address details are correctly stored and an id is assigned to address"
        null != savedStudent.address() && null != savedStudent.address().id() && 1L == savedStudent.address().id()
    }
}
