package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService  {
    List<Person> findAll();
    Optional<Person> findById(Long id);
    Long deleteById(Long id);
    Person save(Person person);
    Person update(PersonUpdateDTO person, Long id);
    Person findByfirstnameAndLastname(String firstname, String lastname);
}
