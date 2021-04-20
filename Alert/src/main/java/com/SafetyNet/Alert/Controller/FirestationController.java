package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Service.FirestationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class FirestationController {

    @Autowired
    FirestationService firestationService;

    static Logger logger = Logger.getLogger(Firestations.class);


    @GetMapping("/firestations")
    public ResponseEntity<List<FirestationDTO>> getAllFirestations() {
        try {
            ResponseEntity  resp = ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationsDTO(firestationService.findAll()));
            logger.info("Api/firestations/ GetAll OK");
            return resp;
        } catch (Exception e) {
            logger.error("Api/firestations error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "firestation/{id}")
    public ResponseEntity getOneById(@PathVariable Long id) {
        try {
            if(firestationService.findById(id).isPresent()){
                ResponseEntity  resp = ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationDTO(firestationService.findById(id).get()));
                logger.info("Api/firestation/id getId OK");
                return resp;

            }else{
                ResponseEntity  resp = new ResponseEntity<>("Station inexistante",HttpStatus.NOT_FOUND);
                logger.info("Api/firestation/id getId NotFound : " + id);
                return resp;
            }
        } catch (Exception e) {
            logger.error("Api/firestation/firestation/id error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping(value = "firestation/delete/{id}") // SUPPRESSION Par ID ou Adresse
    public ResponseEntity deleteOnebyId(@PathVariable Long id) {
        try {
            Long idResponse = firestationService.deleteById(id);
            if(idResponse == id){
                ResponseEntity  resp =  new ResponseEntity<>(idResponse,HttpStatus.OK);
                logger.info("Api/firestation/delete/id OK");
                return  resp;
            }else{
                ResponseEntity  resp =  new ResponseEntity<>("Station inexistante",HttpStatus.NOT_FOUND);
                logger.info("Api/firestation/delete/id => NotFound");
                return  resp;
            }
        } catch (Exception e) {
            logger.error("Api/firestation/delete/id error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/firestation/add")
    public ResponseEntity<FirestationDTO> addFirestation(@RequestBody FirestationDTO firestationDTO) {
        try {
            firestationService.save(FirestationMapper.INSTANCE.firestationDTOtoFirestation(firestationDTO));
            ResponseEntity  resp = ResponseEntity.status(HttpStatus.CREATED).body(firestationDTO);
            logger.info("Api/firestation/add OK");
            return resp;
        } catch (Exception e) {
            logger.error("Api/firestation/add error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/firestation/update/{id}")
    public ResponseEntity<Firestations> updateFirestation(@RequestBody FirestationDTO firestationDTO, @PathVariable Long id) {
        try {
            firestationDTO.setId(id);
            ResponseEntity  resp = ResponseEntity.status(HttpStatus.ACCEPTED).body(firestationService.update(firestationDTO, id));
            logger.info("Api/firestation/update/id OK");
            return resp;
        } catch (Exception e) {
            logger.error("Api/firestation/update/id error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
