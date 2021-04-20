package com.SafetyNet.Alert.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonsByAddressDTO {
    private String stationNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medication;
    private List<String> allergies;
}
