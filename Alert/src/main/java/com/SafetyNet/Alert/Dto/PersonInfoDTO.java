package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDTO {
    private String firstName;
    private String lastName;
    private String adresse;
    private int age;
    private String mail;
    private List<String> medication;
    private List<String> allergies;
}
