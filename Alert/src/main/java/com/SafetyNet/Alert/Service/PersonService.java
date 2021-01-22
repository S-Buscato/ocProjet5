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

    public Person findByfirstnameAndLastname(String firstname, String lastname) {
        try {
            Person p = personRepository.findByfirstnameAndLastname(firstname, lastname);
            return p;
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

    public int update(Person person, Long id) {
        try {
            final Person p = personRepository.findById(id).isPresent() ? personRepository.findById(id).get() : null;
            if (p != null) {
                p.setAddress(person.getAddress());
                p.setCity(person.getCity());
                p.setZip(person.getZip());
                p.setPhone(person.getPhone());
                p.setEmail(person.getEmail());
                personRepository.save(p);
                return 1;
              }else{
                return 0;
            }
        } catch (Exception e) {

        }
        return 0;
    }

    public Long deleteOneByfirstnameAndLastname(String firstname, String lastname) {
        Person p = this.findByfirstnameAndLastname(firstname, lastname);
        try {
            return this.deleteById(p.getId());
        } catch (
                Exception e) {
        }
        return null;
    }
}
