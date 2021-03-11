package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.PersonInfoDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;

public class PersonInfoMapper {
    public static PersonInfoDTO convertPersonToPersonInfoDto(Persons persons, Medicalrecords medicalrecords){
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        personInfoDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        personInfoDTO.setAdresse(persons.getAddress() == null? "" : persons.getAddress());
        personInfoDTO.setMail(persons.getEmail() == null? "" : persons.getEmail());
        personInfoDTO.setMedication(medicalrecords.getMedications() == null?  null : medicalrecords.getMedications());
        personInfoDTO.setAllergies(medicalrecords.getAllergies() == null? null : medicalrecords.getAllergies());
        return personInfoDTO;
    }
}
