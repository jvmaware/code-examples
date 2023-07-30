package com.jvmaware.tcsb.repositories;

import com.jvmaware.tcsb.models.physical.StudentRecordPhysical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRecordRepository extends JpaRepository<StudentRecordPhysical, Long> {
}
