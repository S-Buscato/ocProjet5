package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Model.Medicalrecords;

import java.util.List;
import java.util.Optional;

public interface IMedicalrecordsService {
    List<Medicalrecords> findAll();
    Optional<Medicalrecords> findById(Long id);
/*
    Long deleteById(Long id);
*/
    Medicalrecords save(Medicalrecords person);

    Iterable<Medicalrecords> saveAll(List<Medicalrecords> medicalrecordsList);
/*
    Medicalrecords findByfirstnameAndLastname(String firstname, String lastname);
*/
}
