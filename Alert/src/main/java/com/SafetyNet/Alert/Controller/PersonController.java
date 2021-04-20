package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import com.SafetyNet.Alert.Service.PersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonService personService;

    static Logger logger = Logger.getLogger(PersonController.class);


    @GetMapping("/person")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        try {
            ResponseEntity resp = ResponseEntity.ok(personService.getAllPersons());
            logger.info("api/person (getAll) => ok");
            return resp;
        } catch (Exception e) {
            logger.error("api/person (getAll) => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping(value = "person/delete")
    public ResponseEntity<Long> deleteOneByLastnameFirstname(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Long id = personService.deleteOneByfirstnameAndLastname(firstName, lastName);
            if(id == 0) {
                ResponseEntity resp = ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
                logger.info("api/person/delete => NotFound");
                return resp;

            }else {
                ResponseEntity resp = ResponseEntity.status(HttpStatus.OK).body(id);
                logger.info("api/person/delete => ok");
                return  resp;
            }
        } catch (Exception e) {
            logger.error("api/person => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/person/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        try {
            ResponseEntity resp = ResponseEntity.status(HttpStatus.CREATED).body(personService.save(PersonMapper.convertPersonDtoToPerson(personDTO)));
            logger.info("api/person/add => ok");
            return resp;

        } catch (Exception e) {
            logger.error("api/person/add => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/person/update/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable Long id) {
        try {
            PersonDTO update = personService.update(personDTO, id);
            ResponseEntity resp = ResponseEntity.status(HttpStatus.ACCEPTED).body(update);
            logger.info("api/person/update/id => ok");
            return resp;
        } catch (Exception e) {
            logger.error("api/person/update/id => error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

