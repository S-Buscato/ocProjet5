package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Persons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PersonRepository extends CrudRepository<Persons, Long> {

  Persons findByfirstNameAndLastName(String firstName, String lastName);
  List<Persons> findByAddress(String address);

  @Query("SELECT p.email FROM Persons p WHERE p.city = :city order by p.email")
  Set<String> citizenEmail(@Param("city") String city);
}
