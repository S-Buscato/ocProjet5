package com.SafetyNet.Alert.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicalRecordsDTO {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String birthdate;

    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> allergies;

}
