package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Firestations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirestationRepository extends CrudRepository<Firestations, Long> {
}
