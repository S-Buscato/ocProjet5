package com.SafetyNet.Alert.Dto;

import lombok.*;
import javax.persistence.Id;

@Data
public class PersonDTO {
    @Id
    private long id;

    private String firstname;
    private String lastname;

    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
