package com.SafetyNet.Alert.Dto;

import javax.persistence.Id;

import lombok.*;

@Data
public class PersonUpdateDTO {
    @Id
    private long id;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
