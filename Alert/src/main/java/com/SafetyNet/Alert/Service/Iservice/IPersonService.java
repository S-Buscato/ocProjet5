package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Model.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> findAll();
    Optional<Person> findById(Long id);
    Long deleteById(Long id);
    int save(Person person);
    int update(Person person, Long id);
}
