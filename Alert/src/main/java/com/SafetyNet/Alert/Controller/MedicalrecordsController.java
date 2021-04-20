package com.SafetyNet.Alert.Controller;


import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import org.apache.log4j.Logger;
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

    static Logger logger = Logger.getLogger(MedicalrecordsController.class);

    @GetMapping("/medicalrecords")
    public ResponseEntity<List<MedicalRecordsDTO>> getAllMedicalRecords() {
        try {
            ResponseEntity resp = ResponseEntity.ok(MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecordsService.findAll()));
            logger.info("api/medicalrecords (get all medicalrecords) => ok");
            return resp;
        } catch (Exception e) {
            ResponseEntity resp = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            logger.error("api//medicalrecords (get all medicalrecords) => error : "+ e.getMessage());
            return resp;
        }
    }


    @GetMapping(value = "medicalrecords/{id}")
    public ResponseEntity<MedicalRecordsDTO> getOneById(@PathVariable Long id) {
        try {
            medicalrecordsService.findById(id).isPresent();
            ResponseEntity resp = ResponseEntity.ok(MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecordsService.findById(id).get()));
            logger.info("api/medicalrecords/id => ok");
            return resp;

        } catch (Exception e) {
            logger.error("api/medicalrecords/id => error : "+ e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "medicalrecords/delete")
    public ResponseEntity<Long> deleteOneByLastnameFirstname(@RequestParam String firstname, @RequestParam String lastname) {
        try {
            ResponseEntity resp = new ResponseEntity<>(medicalrecordsService.deleteOneByfirstnameAndLastname(firstname, lastname),HttpStatus.OK);
            logger.info("api/medicalrecords/delete => Ok");
            return resp;

        } catch (Exception e) {
            ResponseEntity resp = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            logger.error("api/medicalrecords/delete => error : "+ e.getMessage());
            return resp;
        }
    }


    @PostMapping("/medicalrecords/add")
    public ResponseEntity<MedicalRecordsDTO> addMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO) {
        try {
            medicalrecordsService.save(MedicalrecordsMapper.INSTANCE.MedicalrecordsDTOtomedicalrecords(medicalRecordsDTO));
            logger.info("api/medicalrecords/add => ok");
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordsDTO);
            return resp;
        } catch (Exception e) {
            ResponseEntity resp = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            logger.error("api/medicalrecords/add => error : "+ e.getMessage());
            return resp;
        }
    }

    @PutMapping(value = "/medicalrecords/update/{id}")
    public ResponseEntity<MedicalRecordsDTO> updateMedicalRecords(@RequestBody MedicalRecordsDTO medicalRecordsDTO, @PathVariable Long id) {
        try {
            medicalRecordsDTO.setId(id);
            ResponseEntity resp = ResponseEntity.status(HttpStatus.ACCEPTED).body(medicalrecordsService.update(medicalRecordsDTO, id));
            logger.info("api/medicalrecords/update/id => ok");
            return resp;
        } catch (Exception e) {
            logger.error("api/medicalrecords/update/id => error : "+ e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
