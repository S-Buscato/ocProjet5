package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Persons;

import java.util.List;
import java.util.Optional;

public interface IPersonService  {
    List<Persons> findAll();
    Optional<Persons> findById(Long id);
    Long deleteById(Long id);
    Persons save(Persons person);
    Persons update(PersonUpdateDTO person, Long id);
    Persons findByfirstNameAndLastName(String firstName, String lastName);

    Iterable<Persons> saveAll(List<Persons> lstPerson);
}
