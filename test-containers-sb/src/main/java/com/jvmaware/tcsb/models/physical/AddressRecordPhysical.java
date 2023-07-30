package com.jvmaware.tcsb.models.physical;

import com.jvmaware.tcsb.models.logical.AddressRecordLogical;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "AddressRecordPhysical")
@Table(name = "ADDRESS")
public class AddressRecordPhysical {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HOUSE_NO", nullable = false)
    private Long house;

    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "CITY", nullable = false)
    private String city;

    public AddressRecordPhysical(){

    }

    public AddressRecordPhysical(AddressRecordLogical address){
        this.id = address.id();
        this.house = address.house();;
        this.street = address.street();
        this.city = address.city();
    }

    public AddressRecordLogical toLogical(){
        return new AddressRecordLogical(this.id, this.house, this.street, this.city);
    }
}
