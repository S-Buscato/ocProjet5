package com.SafetyNet.Alert.Controller;

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
    //TODO : ADD tray catch
    public List<Person> getAllPersons() {
        return personService.findAll();
    }


    @GetMapping(value = "person/{id}")
    //TODO : ADD tray catch
    public Person getOneById(@PathVariable Long id) {
        return personService.findById(id).isPresent() ? personService.findById(id).get(): null ;
    }


    @PostMapping("/person/add")
    public ResponseEntity<Integer> addPerson(@RequestBody Person person) {
        try {
            return new ResponseEntity<>(personService.save(person), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/person/update/{id}")
    public ResponseEntity<Integer> updatePerson(@RequestBody Person person, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(personService.update(person, id), HttpStatus.CREATED);
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

