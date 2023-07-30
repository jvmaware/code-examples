package com.jvmaware.tcsb.models.physical;

import com.jvmaware.tcsb.models.logical.StudentRecordLogical;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "StudentRecordPhysical")
@Table(name = "STUDENT")
public class StudentRecordPhysical {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;


    // Unidirectional
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_ADDRESS_ON_STUDENT"))
    private AddressRecordPhysical address;

    public StudentRecordPhysical(){

    }

    public StudentRecordPhysical(StudentRecordLogical studentRecordLogical){
        this.id = studentRecordLogical.id();
        this.firstName = studentRecordLogical.firstName();
        this.lastName = studentRecordLogical.lastName();
        this.address = studentRecordLogical.address().toPhysical();
    }

    public StudentRecordLogical toLogical(){
        return new StudentRecordLogical(this.id, this.firstName, this.lastName, this.address.toLogical());
    }
}
