package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Medicalrecords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalserviceRepository extends CrudRepository<Medicalrecords, Long> {
    Medicalrecords findByfirstNameAndLastName(String firstName, String lastName);
}
