package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Service.Iservice.IFirestationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirestationService implements IFirestationService {

    @Autowired
    FirestationRepository firestationRepository;

    static Logger logger = Logger.getLogger(MedicalrecordsService.class);


    @Override
    public List<Firestations> findAll() {
        try {
            List<Firestations> ret = StreamSupport.stream(firestationRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            logger.info("get all Firestations success");
            return ret;

        } catch (Exception e) {
            logger.error("get all Firestation error : " + e);
            return null;
        }
    }


    @Override
    public Optional<Firestations> findById(Long id) {
        try {
            logger.info("Firestations find by id success");
            return firestationRepository.findById(id);
        } catch (Exception e) {
            logger.error("Firestation find by id error : " + e);
            return null;
        }
    }


    @Override
    public Long deleteById(Long id) {
        try {
            firestationRepository.deleteById(id);
            logger.info("Firestations delete by id success");
            return id;
        } catch (Exception e) {
            logger.error("Firestation delete by id error : " + e);
            return null;
        }
    }

    @Override
    public Firestations save(Firestations firestation) {
        Firestations f = new Firestations();
        try {
            if (firestationRepository.findFirestationsByAddress(firestation.getAddress()) == null) {
                firestationRepository.save(firestation);
                f.setAddress(firestation.getAddress());
                f.setStation(firestation.getStation());
                f.setId(firestation.getId());
            }
            logger.info("Firestations save one success");

        } catch (Exception e) {
            logger.error("Firestation save one error : " + e);
        }
        return f;
    }

    @Override
    public Firestations update(FirestationDTO firestationDto, Long id) {
        try {
            Firestations p = firestationRepository.findById(id).isPresent() ? firestationRepository.findById(id).get() : null;
            logger.info("Firestations update success");
            return save(FirestationMapper.INSTANCE.firestationUpdateDtoToFirestationUpdate(firestationDto, p));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Iterable<Firestations> saveAll(List<Firestations> firestationsList) {
        try {
            logger.info("Firestations save all success");
            return firestationRepository.saveAll(firestationsList);

        } catch (Exception e) {
            logger.error("Firestation save all error : " + e);
            return null;
        }
    }

    @Override
    public Firestations findByAddress(String address) {
        try{
            logger.info("Firestations find by address success");
            return firestationRepository.findFirestationsByAddress(address);
        }catch (Exception e){
            logger.error("Firestation find by address error : " + e);
            return null;
        }
    }


}
