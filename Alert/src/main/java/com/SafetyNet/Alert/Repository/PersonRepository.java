package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  Person findByfirstnameAndLastname(String firstname, String lastname);
    
}
