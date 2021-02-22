package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Persons;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Persons, Long> {

  Persons findByfirstNameAndLastName(String firstName, String lastName);
    
}
