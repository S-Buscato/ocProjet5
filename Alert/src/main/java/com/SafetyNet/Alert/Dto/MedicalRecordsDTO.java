package com.SafetyNet.Alert.Dto;

import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@AllArgsConstructor
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
