package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Repository.MedicalserviceRepository;
import com.SafetyNet.Alert.Service.Iservice.IFirestationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class FirestationService implements IFirestationService {

    @Autowired
    FirestationRepository firestationRepository;


    @Override
    public List<Firestations> findAll() {
        try {
            List<Firestations> ret = StreamSupport.stream(firestationRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }


    @Override
    public Optional<Firestations> findById(Long id) {
        try {
            return firestationRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    @Override
    public Long deleteById(Long id) {
        try {
            firestationRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Firestations save(Firestations firestation) {
        Firestations f = new Firestations();
        if(firestationRepository.findFirestationsByAddress(firestation.getAddress()) == null){
           firestationRepository.save(firestation);
            f.setAddress(firestation.getAddress());
            f.setStation(firestation.getStation());
            f.setId(firestation.getId());
        }

        return f;
    }

    @Override
    public Firestations update(FirestationDTO firestationDto, Long id) {
        try {
            Firestations p = firestationRepository.findById(id).isPresent() ? firestationRepository.findById(id).get() : null;
            return save(FirestationMapper.INSTANCE.firestationUpdateDtoToFirestationUpdate(firestationDto, p));
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Iterable<Firestations> saveAll(List<Firestations> firestationsList) {
        return firestationRepository.saveAll(firestationsList);
    }

/*    @Override
    public List<Persons> getPersonsByStationNumber(String stationNumber){
        return firestationRepository.getPersonsByStationNumber(stationNumber);
    }*/

    @Override
    public Firestations findByAddress(String address) {
        return firestationRepository.findFirestationsByAddress(address);
    }



}
