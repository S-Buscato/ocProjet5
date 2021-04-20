package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Service.Iservice.IMedicalrecordsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class MedicalrecordsService implements IMedicalrecordsService {

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    MedicalrecordsMapper medicalrecordsMapper;
    static Logger logger = Logger.getLogger(MedicalrecordsService.class);


    @Override
    public List<Medicalrecords> findAll() {
        try {
            List<Medicalrecords> ret = StreamSupport.stream(medicalRecordsRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            logger.info("Medicalrecords findAll success");
            return ret;

        } catch (Exception e) {
            logger.warn("Medicalrecords findAll error : " + e);
            return null;
        }
    }


    @Override
    public Optional<Medicalrecords> findById(Long id) {
        try {
            logger.info("Medicalrecords find by Id success");
            return medicalRecordsRepository.findById(id);
        } catch (Exception e) {
            logger.warn("Medicalrecords find by Id error : " + e);
            return null;
        }
    }


    @Override
    public Long deleteById(Long id) {
        try {
            medicalRecordsRepository.deleteById(id);
            logger.info("Medicalrecords delete by Id success");
            return id;
        } catch (Exception e) {
            logger.warn("Medicalrecords delete by Id error : " + e);
            return null;
        }
    }

    @Override
    public MedicalRecordsDTO save(Medicalrecords medicalrecords) {
        try{
            logger.info("Medicalrecords save success");
            return medicalrecordsMapper.medicalrecordsToMedicalrecordsDTO(medicalRecordsRepository.save(medicalrecords));
        }catch (Exception e){
            logger.warn("Medicalrecords save error : " + e);
            return  null;
        }
    }


    @Override
    public Iterable<Medicalrecords> saveAll(List<Medicalrecords> medicalrecordsList) {
        try{
            logger.info("Medicalrecords save all success");
            return medicalRecordsRepository.saveAll(medicalrecordsList);
        }catch (Exception e){
            logger.warn("Medicalrecords save all error : " + e);
            return null;
        }

    }


    @Override
    public MedicalRecordsDTO update(MedicalRecordsDTO medicalRecordsDTO, Long id) {
        MedicalRecordsDTO medicalRecordsToUpdate = medicalrecordsMapper.convertMedicalRecordsUpdateDtoToMedicalRecordsDTO(medicalRecordsDTO);
        medicalRecordsToUpdate.setId(id);
        Medicalrecords medicalrecords = medicalRecordsRepository.findById(id).get();
        medicalRecordsToUpdate.setFirstName(medicalrecords.getFirstName());
        medicalRecordsToUpdate.setLastName(medicalrecords.getLastName());
        try {
            logger.info("MedicalRecords update found : " + medicalRecordsDTO + " id : " + id);
            return save(medicalrecordsMapper.MedicalrecordsDTOtomedicalrecords(medicalRecordsToUpdate));
        } catch (Exception e) {
            logger.warn("MedicalRecords update not found : " + medicalRecordsDTO + " id : " + id);
            return null;
        }
    }

    @Override
    public Medicalrecords findByfirstnameAndLastname(String firstName, String lastName) {
        try {
            Medicalrecords m = medicalRecordsRepository.findByfirstNameAndLastName(firstName, lastName);
            logger.info("Medicalrecords find by FirstName and LastName success");
            return m;
        } catch (Exception e) {
            logger.warn("Medicalrecords find by FirstName and LAstName : " + e);
            return null;
        }
    }

    public Long deleteOneByfirstnameAndLastname(String firstname, String lastname) {
        Medicalrecords m = this.findByfirstnameAndLastname(firstname, lastname);
        try {
            logger.info("Medicalrecords delete by LastName and FirstName success");
            return this.deleteById(m.getId());
        } catch (Exception e) {
            logger.warn("Medicalrecords delete by LastName and FirstName by Id error : " + e);
            return null;
        }
    }
}
