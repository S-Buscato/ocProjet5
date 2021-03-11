package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.PersonsByAddressDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;

public class PersonByAddressMapper {

    public static PersonsByAddressDTO convertPersonToPersonForStationDTO(Persons persons, Medicalrecords medicalrecords){
        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        personsByAddressDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        personsByAddressDTO.setPhone(persons.getPhone() == null? "" : persons.getPhone());
        personsByAddressDTO.setMedication(medicalrecords.getMedications() == null?  null : medicalrecords.getMedications());
        personsByAddressDTO.setAllergies(medicalrecords.getAllergies() == null? null : medicalrecords.getAllergies());
        return personsByAddressDTO;
    }
}
