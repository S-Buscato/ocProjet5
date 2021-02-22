package com.SafetyNet.Alert.Controller;


import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Dto.MedicalRecordsUpdateDTO;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Service.FirestationService;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MedicalrecordsController {

    @Autowired
    MedicalrecordsService medicalrecordsService;


    @GetMapping("/medicalrecords")
    public ResponseEntity<List<MedicalRecordsDTO>> getAllFirestations() {
        try {
            return ResponseEntity.ok(MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecordsService.findAll()));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "medicalrecords/{id}")
    public ResponseEntity<MedicalRecordsDTO> getOneById(@PathVariable Long id) {
        try {
            return medicalrecordsService.findById(id).isPresent() ?
                    ResponseEntity.ok(MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecordsService.findById(id).get()))
                    :
                    ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(value = "medicalrecords/delete")
    public ResponseEntity<Long> getOneByLastnameFirstname(@RequestParam String firstname, @RequestParam String lastname) {
        try {
            return new ResponseEntity<>(medicalrecordsService.deleteOneByfirstnameAndLastname(firstname, lastname),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/medicalrecords/add")
    public ResponseEntity<MedicalRecordsDTO> addMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        try {
            medicalrecordsService.save(MedicalrecordsMapper.INSTANCE.MedicalrecordsDTOtomedicalrecords(medicalRecordsDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordsDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/medicalrecords/update/{id}")
    public ResponseEntity<Medicalrecords> updateMedicalRecords(@RequestBody MedicalRecordsUpdateDTO medicalRecordsUpdateDTO, @PathVariable Long id) {
        try {
            medicalRecordsUpdateDTO.setId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(medicalrecordsService.update(medicalRecordsUpdateDTO, id));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
