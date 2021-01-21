package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Model.Person;
import com.SafetyNet.Alert.Repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {


    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        try {
            List<Person> ret = StreamSupport.stream(personRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }

    public Optional<Person> findById(Long id) {
        try {
            return personRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    public Long deleteById(Long id) {
        try {
            personRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    public int save(Person person) {
        try {
            personRepository.save(person);
        } catch (Exception e) {

        }
        return 0;
    }

    //TODO : faire controle si ID existe
    public int update(Person person, Long id) {
        try {
            if(personRepository.existsById(id)){
                person.setId(id);
                personRepository.save(person);
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
