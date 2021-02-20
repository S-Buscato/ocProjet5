package com.SafetyNet.Alert.Model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name="persons")
public class Persons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Person(){
        super();
    }

/*    @ManyToOne
    Firestation firestation;

    @OneToOne
    Medicalrecords medicalrecords;*/

}
