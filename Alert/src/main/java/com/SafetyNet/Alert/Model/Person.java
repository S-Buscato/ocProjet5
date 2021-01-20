package com.SafetyNet.Alert.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
