package com.SafetyNet.Alert.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
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
