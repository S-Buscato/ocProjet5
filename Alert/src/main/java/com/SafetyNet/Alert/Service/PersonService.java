package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService implements IPersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    static Logger logger = Logger.getLogger(PersonService.class);

    public List<PersonDTO> getAllPersons() {
        try{
            logger.info("Persons findAll success");
            return ((List<Persons>) personRepository
                    .findAll())
                    .stream()
                    .map(PersonMapper::convertPersonToPersonDto).collect(Collectors.toList());

        }catch (Exception e){
            logger.warn("Persons findAll error : " + e);
            return null;
        }
    }

    @Override
    public PersonDTO findByfirstNameAndLastName(String firstName, String lastName) {
        try {
            logger.info("Persons find by LastName and FirstName success : " + firstName + " " +  lastName);
            return personMapper.convertPersonToPersonDto(personRepository.findByfirstNameAndLastName(firstName, lastName));
//            Persons p = personRepository.findByfirstNameAndLastName(firstName, lastName);
//            return p;
        } catch (Exception e) {
            logger.warn("Personsfind by LastName and FirstName error : " + firstName + " " +  lastName +" -- " + e);
            return null;
        }
    }

    @Override
    public List<Persons> findByAddress(String address) {
        try {
            List<Persons> ret = StreamSupport.stream(personRepository.findByAddress(address).spliterator(),
                    false).collect(Collectors.toList());
            logger.info("Persons find by Address success : " + address);
            return ret;

        } catch (Exception e) {
            logger.warn("Persons find by Address error : " + address + " -- " + e);
            return null;
        }
    }

    @Override
    public Iterable<Persons> saveAll(List<Persons> lstPerson) {
        try {
            logger.info("Persons save all success");
            return personRepository.saveAll(lstPerson);
        }catch (Exception e){
            logger.warn("Persons save all error : " + e);
            return null;
        }
    }


    @Override
    public Optional<Persons> findById(Long id) {
        try {
            logger.info("Persons find by id success : " + id);
            return personRepository.findById(id);
        } catch (Exception e) {
            logger.warn("Persons find by id error : " + id +" -- " + e);
            return null;
        }
    }


    @Override
    public Long deleteById(Long id) {
        try {
            personRepository.deleteById(id);
            logger.info("Persons Repository deleteById : " + id);
            return id;
        } catch (Exception e) {
            logger.warn("Persons Repository delete by id not found : " + id  + " -- " + e);
            return null;
        }
    }

    @Override
    public PersonDTO save(Persons person) {
        try {
            logger.info("Persons Repository save person success" );
            return personMapper.convertPersonToPersonDto(personRepository.save(person));
        }catch (Exception e){
            logger.warn("Persons Repository save error : " + e);
            return null;
        }
    }

    @Override
    public PersonDTO update(PersonDTO personDto, Long id) {
        PersonDTO personsToUpdate = personMapper.convertPersonUpdateDtoToPersonDto(personDto);
        personsToUpdate.setId(id);
        Persons persons = personRepository.findById(id).get();
        personsToUpdate.setFirstName(persons.getFirstName());
        personsToUpdate.setLastName(persons.getLastName());
        try {
            logger.info("Persons update found : " + personDto + " id : " + id);
            return save(personMapper.convertPersonDtoToPerson(personsToUpdate));
        } catch (Exception e) {
            logger.warn("Persons update not found : " + personDto + " id : " + id);
            return null;
        }
    }

    public Long deleteOneByfirstnameAndLastname(String firstName, String lastName) {
        PersonDTO p = this.findByfirstNameAndLastName(firstName, lastName);
        try{
            Long id = this.deleteById(p.getId());
            if(id != null){
                logger.info("Persons to delete found : " + firstName + " " + lastName);
            }
            return id;
        }catch (Exception e){
            logger.warn("Persons to delete not found : " + firstName + " " + lastName);
            return 0L;
        }
    }

}
