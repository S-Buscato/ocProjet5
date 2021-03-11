package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;

import java.util.List;
import java.util.Optional;

public interface IPersonService  {
    List<Persons> findAll();
    Optional<Persons> findById(Long id);
    Long deleteById(Long id);
    PersonDTO save(Persons person);
    PersonDTO update(PersonDTO personDTO, Long id);
    PersonDTO findByfirstNameAndLastName(String firstName, String lastName);
    List<Persons> findByAddress(String Address);
    Iterable<Persons> saveAll(List<Persons> lstPerson);
}
