package com.jvmaware.tcsb.models.logical;

import com.jvmaware.tcsb.models.physical.AddressRecordPhysical;

/**
 * A logical record representing an address record.
 *
 * @param id     unique identifier
 * @param house  house number
 * @param street street name
 * @param city   city of residence
 * @author gaurs
 */
public record AddressRecordLogical(Long id, Long house, String street, String city) {

    public AddressRecordPhysical toPhysical(){
        return new AddressRecordPhysical(this);
    }
}
