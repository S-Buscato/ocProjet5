package com.SafetyNet.Alert.Dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Id;
import java.util.List;

@Data
public class MedicalRecordsUpdateDTO {
    @Id
    private long id;
    private String birthdate;
    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> allergies;
}
