package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
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
