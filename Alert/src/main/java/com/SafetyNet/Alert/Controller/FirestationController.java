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
            return ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationsDTO(firestationService.findAll()));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "firestation/{id}")
    public ResponseEntity getOneById(@PathVariable Long id) {
        try {
            return firestationService.findById(id).isPresent() ?
                    ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationDTO(firestationService.findById(id).get()))
                    :
                    new ResponseEntity<>("Station inexistante",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping(value = "firestation/delete/{id}") // SUPPRESSION Par ID ou Adresse
    public ResponseEntity deleteOnebyId(@PathVariable Long id) {
        try {
            Long idResponse = firestationService.deleteById(id);
            return  idResponse == id?
                    new ResponseEntity<>(idResponse,HttpStatus.OK)
                    :
                    new ResponseEntity<>("Station inexistante",HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/firestation/add")
    public ResponseEntity<FirestationDTO> addFirestation(@RequestBody FirestationDTO firestationDTO) {
        try {
            firestationService.save(FirestationMapper.INSTANCE.firestationDTOtoFirestation(firestationDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(firestationDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/firestation/update/{id}")
    public ResponseEntity<Firestations> updateFirestation(@RequestBody FirestationDTO firestationDTO, @PathVariable Long id) {
        try {
            firestationDTO.setId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(firestationService.update(firestationDTO, id));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
