package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;

import java.util.List;
import java.util.Optional;

public interface IMedicalrecordsService {
    List<Medicalrecords> findAll();
    Optional<Medicalrecords> findById(Long id);
    Long deleteById(Long id);
    MedicalRecordsDTO save(Medicalrecords person);
    Iterable<Medicalrecords> saveAll(List<Medicalrecords> medicalrecordsList);

    MedicalRecordsDTO update(MedicalRecordsDTO medicalRecordsDTO, Long id);

    Medicalrecords findByfirstnameAndLastname(String firstname, String lastname);
}
