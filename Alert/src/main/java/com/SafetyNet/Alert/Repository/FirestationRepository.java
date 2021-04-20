package com.SafetyNet.Alert.Repository;

import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Persons;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationRepository extends CrudRepository<Firestations, Long> {
    Firestations findFirestationsByAddress(String address);
    Firestations findByStation(String i);

    @Query("SELECT p  FROM Persons p, Firestations f " +
            "WHERE p.address = f.address " +
            "AND f.station = :stationNumber")
    List<Persons> getPersonsByStationNumber(@Param("stationNumber") String station);

}
