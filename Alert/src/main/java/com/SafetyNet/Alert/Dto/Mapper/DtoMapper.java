package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Service;

@Service
public class DtoMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public ChildrenByAddressDTO convertPersonToChildrenByAddressDTO(Persons persons) {
        ChildrenByAddressDTO childrenByAddressDTO = new ChildrenByAddressDTO();
        childrenByAddressDTO.setFirstName(persons.getFirstName() == null ? "" : persons.getFirstName());
        childrenByAddressDTO.setLastName(persons.getLastName() == null ? "" : persons.getLastName());
        return childrenByAddressDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public FamilyMemberDTO convertPersonToFamillyMemberDTO(Persons persons) {
        FamilyMemberDTO FamilyMemberDTO = new FamilyMemberDTO();
        FamilyMemberDTO.setFirstName(persons.getFirstName() == null ? "" : persons.getFirstName());
        FamilyMemberDTO.setLastName(persons.getLastName() == null ? "" : persons.getLastName());
        return FamilyMemberDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public PersonsByAddressDTO convertPersonToPersonForStationDTO(Persons persons, Medicalrecords medicalrecords) {
        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setLastName(persons.getLastName() == null ? "" : persons.getLastName());
        personsByAddressDTO.setFirstName(persons.getFirstName() == null ? "" : persons.getFirstName());
        personsByAddressDTO.setPhone(persons.getPhone() == null ? "" : persons.getPhone());
        personsByAddressDTO.setMedication(medicalrecords.getMedications() == null ? null : medicalrecords.getMedications());
        personsByAddressDTO.setAllergies(medicalrecords.getAllergies() == null ? null : medicalrecords.getAllergies());
        return personsByAddressDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public PersonForStationDTO convertPersonToPersonForStationDTO(Persons persons) {
        PersonForStationDTO personForStationDTO = new PersonForStationDTO();
        personForStationDTO.setFirstName(persons.getFirstName() == null ? "" : persons.getFirstName());
        personForStationDTO.setLastName(persons.getLastName() == null ? "" : persons.getLastName());
        personForStationDTO.setAddress(persons.getAddress() == null ? "" : persons.getAddress());
        personForStationDTO.setPhone(persons.getPhone() == null ? "" : persons.getPhone());
        return personForStationDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public PersonInfoDTO convertPersonToPersonInfoDto(Persons persons, Medicalrecords medicalrecords){
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
