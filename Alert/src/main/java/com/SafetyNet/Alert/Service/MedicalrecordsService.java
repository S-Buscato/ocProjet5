package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsUpdateDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Repository.MedicalserviceRepository;
import com.SafetyNet.Alert.Service.Iservice.IMedicalrecordsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Data
@Service
public class MedicalrecordsService implements IMedicalrecordsService {

    @Autowired
    MedicalserviceRepository medicalserviceRepository;

    @Override
    public List<Medicalrecords> findAll() {
        try {
            List<Medicalrecords> ret = StreamSupport.stream(medicalserviceRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }


    @Override
    public Optional<Medicalrecords> findById(Long id) {
        try {
            return medicalserviceRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    @Override
    public Long deleteById(Long id) {
        try {
            medicalserviceRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Medicalrecords save(Medicalrecords medicalrecords) {
        return medicalserviceRepository.save(medicalrecords);

    }


    @Override
    public Iterable<Medicalrecords> saveAll(List<Medicalrecords> medicalrecordsList) {
        return medicalserviceRepository.saveAll(medicalrecordsList);
    }

    @Override
    public Medicalrecords update(MedicalRecordsUpdateDTO medicalRecordsUpdateDTO, Long id) {
        try {
            Medicalrecords m = medicalserviceRepository.findById(id).isPresent() ? medicalserviceRepository.findById(id).get() : null;
            return save(MedicalrecordsMapper.INSTANCE.medicalRecordsUpdateDtoToMedicalRecordsUpdate(medicalRecordsUpdateDTO, m));
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Medicalrecords findByfirstnameAndLastname(String firstName, String lastName) {
        try {
            Medicalrecords m = medicalserviceRepository.findByfirstNameAndLastName(firstName, lastName);
            return m;
        } catch (Exception e) {

        }

        return null;
    }
    public Long deleteOneByfirstnameAndLastname(String firstname, String lastname) {
        Medicalrecords m = this.findByfirstnameAndLastname(firstname, lastname);
        try {
            return this.deleteById(m.getId());
        } catch (
                Exception e) {
        }
        return null;
    }
}
