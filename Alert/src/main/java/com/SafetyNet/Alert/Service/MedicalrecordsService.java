package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Dto.MedicalRecordsUpdateDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
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
    MedicalRecordsRepository medicalRecordsRepository;

    @Override
    public List<Medicalrecords> findAll() {
        try {
            List<Medicalrecords> ret = StreamSupport.stream(medicalRecordsRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }


    @Override
    public Optional<Medicalrecords> findById(Long id) {
        try {
            return medicalRecordsRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    @Override
    public Long deleteById(Long id) {
        try {
            medicalRecordsRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Medicalrecords save(Medicalrecords medicalrecords) {
        return medicalRecordsRepository.save(medicalrecords);

    }


    @Override
    public Iterable<Medicalrecords> saveAll(List<Medicalrecords> medicalrecordsList) {
        return medicalRecordsRepository.saveAll(medicalrecordsList);
    }

/*    @Override
    public Medicalrecords update(MedicalRecordsDTO medicalRecordsDTO, Long id) {
        try {
            MedicalRecordsDTO medicalRecordsUpdateDTO = MedicalrecordsMapper.INSTANCE.convertMedicalRecordsUpdateDtoToMedicalRecordsDTO(medicalRecordsDTO);
            personsToUpdate.setId(id);
            Persons persons = personRepository.findById(id).get();
            personsToUpdate.setFirstName(persons.getFirstName());
            personsToUpdate.setLastName(persons.getLastName());
            Medicalrecords m = medicalRecordsRepository.findById(id).isPresent() ? medicalRecordsRepository.findById(id).get() : null;

            return save(MedicalrecordsMapper.INSTANCE.convertMedicalRecordsUpdateDtoToMedicalRecordsDTO(medicalRecordsUpdateDTO, m));
        } catch (Exception e) {

        }
        return null;
    }*/

    @Override
    public Medicalrecords findByfirstnameAndLastname(String firstName, String lastName) {
        try {
            Medicalrecords m = medicalRecordsRepository.findByfirstNameAndLastName(firstName, lastName);
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
