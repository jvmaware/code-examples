package com.jvmaware.streamingserver.repositories;

import com.jvmaware.streamingserver.model.physical.EmployeePhysical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeePhysical, Long> {

    Stream<EmployeePhysical> findAllByActive(Boolean active);
}
