package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    //findByfirstname etc
}
