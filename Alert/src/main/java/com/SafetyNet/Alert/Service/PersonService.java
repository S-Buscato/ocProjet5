package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.IPersonService;
import lombok.Data;
import org.apache.log4j.Logger;
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
    PersonMapper personMapper;

    static Logger logger = Logger.getLogger(CommonService.class);


/*    @Override
    public List<Persons> findAll() {
        try {
            List<Persons> ret = StreamSupport.stream(personRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }*/

    public List<PersonDTO> getAllPersons() {
        try{
            logger.info("get all persons success");
            return ((List<Persons>) personRepository
                    .findAll())
                    .stream()
                    .map(PersonMapper::convertPersonToPersonDto).collect(Collectors.toList());

        }catch (Exception e){
            logger.warn("persons get all not found");
            return null;
        }
    }

    @Override
    public PersonDTO findByfirstNameAndLastName(String firstName, String lastName) {
        try {
            logger.info("person found : " + firstName + " " +  lastName);
            return personMapper.convertPersonToPersonDto(personRepository.findByfirstNameAndLastName(firstName, lastName));
//            Persons p = personRepository.findByfirstNameAndLastName(firstName, lastName);
//            return p;
        } catch (Exception e) {
            logger.warn("person not found : " + firstName + " " +  lastName);
            return null;
        }
    }

    @Override
    public List<Persons> findByAddress(String address) {
        try {
            List<Persons> ret = StreamSupport.stream(personRepository.findByAddress(address).spliterator(),
                    false).collect(Collectors.toList());
            logger.info("address found : " + address);
            return ret;

        } catch (Exception e) {
            logger.warn("address not found : " + address);
            return null;
        }
    }

    @Override
    public Iterable<Persons> saveAll(List<Persons> lstPerson) {
        try {
            logger.info("save persons list succes");
            return personRepository.saveAll(lstPerson);
        }catch (Exception e){
            logger.warn("save persons list error : " + e);
            return null;
        }
    }


    @Override
    public Optional<Persons> findById(Long id) {
        try {
            logger.info("person id found : " + id);
            return personRepository.findById(id);
        } catch (Exception e) {
            logger.warn("persons not found : " + id);
            return null;
        }
    }


    @Override
    public Long deleteById(Long id) {
        try {
            personRepository.deleteById(id);
            logger.info("id persons to delete found : " + id);
            return id;
        } catch (Exception e) {
            logger.warn("id persons to delete not found : " + id);
            return null;
        }
    }

    @Override
    public PersonDTO save(Persons person) {
        return personMapper.convertPersonToPersonDto(personRepository.save(person));
    }

    @Override
    public PersonDTO update(PersonDTO personDto, Long id) {
        PersonDTO personsToUpdate = personMapper.convertPersonUpdateDtoToPersonDto(personDto);
        personsToUpdate.setId(id);
        Persons persons = personRepository.findById(id).get();
        personsToUpdate.setFirstName(persons.getFirstName());
        personsToUpdate.setLastName(persons.getLastName());
        try {
            logger.info("id persons to update found : " + personDto + " id : " + id);
            return save(personMapper.convertPersonDtoToPerson(personsToUpdate));
        } catch (Exception e) {
            logger.warn("id persons to update not found : " + personDto + " id : " + id);
            return null;
        }
    }

    public Long deleteOneByfirstnameAndLastname(String firstName, String lastName) {
        PersonDTO p = this.findByfirstNameAndLastName(firstName, lastName);
        try{
            logger.info("persons to delete found : " + firstName + " " + lastName);
            return this.deleteById(p.getId());
        }catch (Exception e){
            logger.warn("persons to delete not found : " + firstName + " " + lastName);
            return 0L;
        }
    }

}
