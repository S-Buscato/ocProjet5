package com.SafetyNet.Alert.Model;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TBL_PERSONS")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter(AccessLevel.NONE)
    private String firstname;
    @Setter(AccessLevel.NONE)
    private String lastname;

    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
