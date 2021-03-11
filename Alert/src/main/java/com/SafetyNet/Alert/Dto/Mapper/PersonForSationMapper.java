package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.PersonForStationDTO;
import com.SafetyNet.Alert.Model.Persons;

public class PersonForSationMapper {

    public static PersonForStationDTO convertPersonToPersonForStationDTO(Persons persons){
        PersonForStationDTO personForStationDTO = new PersonForStationDTO();
        personForStationDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        personForStationDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        personForStationDTO.setAddress(persons.getAddress() == null? "" : persons.getAddress());
        personForStationDTO.setPhone(persons.getPhone() == null? "" : persons.getPhone());
        return personForStationDTO;
    }
}
