package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Person;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.IPersonService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class PersonService implements IPersonService {



    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        try {
            List<Person> ret = StreamSupport.stream(personRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Person findByfirstnameAndLastname(String firstname, String lastname) {
        try {
            Person p = personRepository.findByfirstnameAndLastname(firstname, lastname);
            return p;
        } catch (Exception e) {

        }

        return null;
    }
    @Override
    public Optional<Person> findById(Long id) {
        try {
            return personRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    @Override
    public Long deleteById(Long id) {
        try {
            personRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Person save(Person person) {
            return personRepository.save(person);

    }

    @Override
    public Person update(PersonUpdateDTO personDto, Long id) {
        try {
            Person p = personRepository.findById(id).isPresent() ? personRepository.findById(id).get() : null;
            return save(PersonMapper.INSTANCE.personUpdateDtoToPersonUpdate(personDto, p));
        } catch (Exception e) {

        }
        return null;
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
