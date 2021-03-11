package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Medicalrecords;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalserviceRepository extends CrudRepository<Medicalrecords, Long> {
    Medicalrecords findByfirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT m.birthdate FROM Medicalrecords m WHERE m.lastName = :lastname AND m.firstName = :firstName")
    String getBirhdate(@Param("firstName") String firsName, @Param("lastname") String lastName);
}
