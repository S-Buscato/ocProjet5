package com.SafetyNet.Alert.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@Entity
@Table(name="firestations")
public class Firestations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;

    public Firestations(){
        super();
    }

/*    @OneToMany(mappedBy="person")
    Collection<Person> person;*/
}
