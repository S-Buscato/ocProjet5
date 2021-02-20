package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Person;
import com.SafetyNet.Alert.Service.PersonService;
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


    @GetMapping("/person")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        try {
            return ResponseEntity.ok(PersonMapper.INSTANCE.personToPersonsDTO(personService.findAll()));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "person/{id}")
    public ResponseEntity<PersonDTO> getOneById(@PathVariable Long id) {
        try {
            return personService.findById(id).isPresent() ?
                    ResponseEntity.ok(PersonMapper.INSTANCE.personToPersonDTO(personService.findById(id).get()))
                    :
                    ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "person/delete")
    public ResponseEntity<Long> getOneByLastnameFirstname(@RequestParam String firstname, @RequestParam String lastname) {
        try {
            return new ResponseEntity<>(personService.deleteOneByfirstnameAndLastname(firstname, lastname),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/person/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO) {
        try {
            personService.save(PersonMapper.INSTANCE.personDTOtoPerson(personDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(personDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/person/update/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonUpdateDTO personDTO, @PathVariable Long id) {
        try {
            personDTO.setId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(personService.update(personDTO, id));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/person/{id}")
    public ResponseEntity<Long> deletePerson(@PathVariable long id) {
        try {
            return new ResponseEntity<>(personService.deleteById(id), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

