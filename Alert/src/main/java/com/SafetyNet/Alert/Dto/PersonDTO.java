package com.SafetyNet.Alert.Dto;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
