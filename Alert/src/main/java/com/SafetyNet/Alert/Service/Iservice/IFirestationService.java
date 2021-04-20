package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.HouseholdDTO;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Persons;
import org.springframework.data.repository.query.Param;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IFirestationService {
    List<Firestations> findAll();
    Optional<Firestations> findById(Long id);
    Long deleteById(Long id);
    Firestations save(Firestations firestation);
    Firestations update(FirestationDTO person, Long id);

    Iterable<Firestations> saveAll(List<Firestations> firestationsList);

    Firestations findByAddress(String address);

}
