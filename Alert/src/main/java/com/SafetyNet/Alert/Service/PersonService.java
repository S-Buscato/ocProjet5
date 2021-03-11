package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;
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
    @Autowired
    MedicalrecordsService medicalrecordsService;
    @Autowired
    FirestationService firestationService;
    @Autowired
    CommonService commonService;


    @Override
    public List<Persons> findAll() {
        try {
            List<Persons> ret = StreamSupport.stream(personRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }

    public List<PersonDTO> getAllPersons() {
        return ((List<Persons>) personRepository
                .findAll())
                .stream()
                .map(PersonMapper::convertPersonToPersonDto).collect(Collectors.toList());
    }

    @Override
    public PersonDTO findByfirstNameAndLastName(String firstName, String lastName) {
        try {
            return PersonMapper.convertPersonToPersonDto(personRepository.findByfirstNameAndLastName(firstName, lastName));
//            Persons p = personRepository.findByfirstNameAndLastName(firstName, lastName);
//            return p;
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public List<Persons> findByAddress(String address) {
        try {
            List<Persons> ret = StreamSupport.stream(personRepository.findByAddress(address).spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;    }

    @Override
    public Iterable<Persons> saveAll(List<Persons> lstPerson) {
        return personRepository.saveAll(lstPerson);
    }

    @Override
    public Optional<Persons> findById(Long id) {
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
    public PersonDTO save(Persons person) {
            personRepository.save(person);
            PersonDTO personDToSaved = findByfirstNameAndLastName(person.getFirstName(), person.getLastName());
            return findById(personDToSaved.getId()).isPresent()? PersonMapper.convertPersonToPersonDto(findById(personDToSaved.getId()).get()) : null;
    }

    @Override
    public PersonDTO update(PersonDTO personDto, Long id) {
        PersonDTO personsToUpdate = PersonMapper.convertPersonUpdateDtoToPersonDto(personDto);
        personsToUpdate.setId(id);
        Persons persons = personRepository.findById(id).get();
        personsToUpdate.setFirstName(persons.getFirstName());
        personsToUpdate.setLastName(persons.getLastName());
        try {
            return save(PersonMapper.convertPersonDtoToPerson(personsToUpdate));
           // return save(PersonMapper.INSTANCE.personUpdateDtoToPersonUpdate(personDto, p));
        } catch (Exception e) {

        }
        return null;
    }

    public Long deleteOneByfirstnameAndLastname(String firstName, String lastName) {
        PersonDTO p = this.findByfirstNameAndLastName(firstName, lastName);
        try {
            return this.deleteById(p.getId());
        } catch (
                Exception e) {
        }
        return null;
    }

}
