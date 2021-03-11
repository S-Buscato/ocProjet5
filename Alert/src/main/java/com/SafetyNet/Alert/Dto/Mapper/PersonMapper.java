package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Persons;
import org.mapstruct.BeanMapping;
/*
import org.mapstruct.Mapper;
*/
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
@Mapper(componentModel="spring")
*/
public class PersonMapper {


 /*   PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonDTO personToPersonDTO(Persons entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Persons personDTOtoPerson(PersonDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<PersonDTO> personToPersonsDTO(List<Persons> persons);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Persons personUpdateDtoToPersonUpdate(PersonUpdateDTO person,  @MappingTarget Persons entity);*/

    public static PersonDTO convertPersonToPersonDto(Persons persons){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(persons.getId());
        personDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        personDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        personDTO.setAddress(persons.getAddress() == null? "" : persons.getAddress());
        personDTO.setPhone(persons.getPhone() == null? "" : persons.getPhone());
        personDTO.setCity(persons.getCity() == null? "" : persons.getCity());
        personDTO.setZip(persons.getZip() == null? "" : persons.getZip());
        personDTO.setEmail(persons.getEmail() == null ? "" : persons.getEmail());
        return personDTO;
    }

    public static Persons convertPersonDtoToPerson(PersonDTO personDTO){
        Persons persons = new Persons();
        persons.setId(personDTO.getId());
        persons.setFirstName(personDTO.getFirstName() == null? "" : personDTO.getFirstName());
        persons.setLastName(personDTO.getLastName() == null? "" : personDTO.getLastName());
        persons.setAddress(personDTO.getAddress() == null? "" : personDTO.getAddress());
        persons.setPhone(personDTO.getPhone() == null? "" : personDTO.getPhone());
        persons.setCity(personDTO.getCity() == null? "" : personDTO.getCity());
        persons.setZip(personDTO.getZip() == null? "" : personDTO.getZip());
        persons.setEmail(personDTO.getEmail() == null ? "" : personDTO.getEmail());
        return persons;
    }
    public static PersonDTO convertPersonUpdateDtoToPersonDto(PersonDTO personDTO){
        PersonDTO personDtoToSave = new PersonDTO();
        personDtoToSave.setId(personDTO.getId());
        personDtoToSave.setAddress(personDTO.getAddress() == null? "" : personDTO.getAddress());
        personDtoToSave.setPhone(personDTO.getPhone() == null? "" : personDTO.getPhone());
        personDtoToSave.setCity(personDTO.getCity() == null? "" : personDTO.getCity());
        personDtoToSave.setZip(personDTO.getZip() == null? "" : personDTO.getZip());
        personDtoToSave.setEmail(personDTO.getEmail() == null ? "" : personDTO.getEmail());
        return personDtoToSave;
    }


}
