package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;

import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class FirestationController {

    @Autowired
    FirestationService firestationService;


    @GetMapping("/firestation")
    public ResponseEntity<List<FirestationDTO>> getAllFirestations() {
        try {
            return ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationsDTO(firestationService.findAll()));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "firestation/{id}")
    public ResponseEntity<FirestationDTO> getOneById(@PathVariable Long id) {
        try {
            return firestationService.findById(id).isPresent() ?
                    ResponseEntity.ok(FirestationMapper.INSTANCE.firestationToFirestationDTO(firestationService.findById(id).get()))
                    :
                    ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "firestation/delete/{id}") // SUPPRESSION Par ID ou Adresse
    public ResponseEntity<Long> deleteOnebyId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(firestationService.deleteById(id),
                    HttpStatus.OK);

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
