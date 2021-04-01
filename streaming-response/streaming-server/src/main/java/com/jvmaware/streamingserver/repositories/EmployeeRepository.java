package com.jvmaware.streamingserver.repositories;

import com.jvmaware.streamingserver.model.physical.EmployeePhysical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.READ_ONLY;
import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeePhysical, Long> {

    /**
     * The QueryHint and other instructions to the persistence context are actually underlying
     * DB specific. There is a possibility that the actual ORM implementation or the underlying DB
     * does not honor these.
     *
     * @return employee stream
     */
    @QueryHints(value = {
            @QueryHint(name = HINT_FETCH_SIZE, value = "50"),
            @QueryHint(name = HINT_CACHEABLE, value = "false"),
            @QueryHint(name = READ_ONLY, value = "true")
    })
    @Query("SELECT EMP FROM Employees EMP")
    Stream<EmployeePhysical> getAllEmployees();
}
