package com.jvmaware.tcsb.models.logical;

import com.jvmaware.tcsb.models.physical.StudentRecordPhysical;

/**
 * A logical student record corresponding to student details as
 * used in the API
 *
 * @param id        unique student identifier
 * @param firstName Student's first name
 * @param lastName  Student's last name
 * @param address   address details
 * @author gaurs
 */
public record StudentRecordLogical(Long id, String firstName, String lastName,
                                   AddressRecordLogical address) {

    public StudentRecordPhysical toPhysical() {
        return new StudentRecordPhysical(this);
    }
}
