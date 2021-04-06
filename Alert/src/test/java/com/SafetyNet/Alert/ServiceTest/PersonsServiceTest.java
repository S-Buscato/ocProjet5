package com.SafetyNet.Alert.ServiceTest;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureTestDatabase
class PersonServiceTest {


    @Autowired
    PersonService personService;

    @Autowired
    PersonMapper personMapper;

    @MockBean
    PersonRepository personRepository;

    @Test
    @DisplayName("test Find by firstName & lastName Succes")
    void testFindbyFirstNameAndLastName() {
        Persons persons = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        when(personRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(persons);

        Assertions.assertEquals(personService.findByfirstNameAndLastName("John", "Doe").getFirstName(), persons.getFirstName());
        Assertions.assertEquals(personService.findByfirstNameAndLastName("John", "Doe").getPhone(), persons.getPhone());
        Assertions.assertEquals(personService.findByfirstNameAndLastName("John", "Doe").getEmail(), persons.getEmail());
        verify(personRepository, times(3)).findByfirstNameAndLastName("John", "Doe");
    }

    @Test
    @DisplayName("test Find by firstName & lastName not exists Succes")
    void testFindbyFirstNameAndLastNameNotExist() {

        when(personRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(null);

        Assertions.assertTrue(personService.findByfirstNameAndLastName("John", "Doe") == null);
        verify(personRepository, times(1)).findByfirstNameAndLastName("John", "Doe");
    }

    @Test
    @DisplayName("test Find by address not exists Succes")
    void testFindbyAddressNotExist() {

        when(personRepository.findByAddress("mauvaise adresse")).thenReturn(null);

        Assertions.assertTrue(personService.findByAddress("mauvaise adresse") == null);
        verify(personRepository, times(1)).findByAddress("mauvaise adresse");
    }

    @Test
    @DisplayName("test add one Person Success")
    void testAddPerson() {

        Persons persons = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        when(personRepository.save(persons)).thenReturn(persons);

        Assertions.assertEquals(personService.save(persons).getFirstName(), persons.getFirstName());
        Assertions.assertEquals(personService.save(persons).getLastName(), persons.getLastName());
        verify(personRepository, times(2)).save(eq(persons));
    }

    @Test
    @DisplayName("test update one Person Success")
    void testUpdatePerson() {

        Persons persons = new Persons();
        persons.setId(1);
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        PersonDTO persons2 = new PersonDTO();
        persons2.setFirstName("Jack");
        persons2.setLastName("Sparrow");
        persons2.setAddress("Bvd des Caraïbes");
        persons2.setZip("33300");
        persons2.setEmail("jack.D@nomail.com");
        persons2.setCity("Blackbird");
        persons2.setPhone("0102020202");


        PersonDTO personToUpdate = persons2;
        personToUpdate.setId(1);
        personToUpdate.setFirstName(persons.getFirstName());
        personToUpdate.setLastName(persons.getLastName());

        Long id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.of(persons));
        when(personRepository.save(personMapper.convertPersonDtoToPerson(persons2))).thenReturn(personMapper.convertPersonDtoToPerson(personToUpdate));

        Assertions.assertEquals("John", personService.update(persons2, id).getFirstName());
        Assertions.assertEquals("Doe", personService.update(persons2, id).getLastName());
        Assertions.assertEquals("Blackbird", personService.update(persons2, id).getCity());

        verify(personRepository, times(3)).save(eq(personMapper.convertPersonDtoToPerson(personToUpdate)));
    }

    @Test
    @DisplayName("test add 2 Persons Success")
    void testAdd2Persons() {
        List<Persons> personsList = new ArrayList<>();
        Persons persons = new Persons();
        Persons person2 = new Persons();
        persons.setId(1);
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        person2.setId(2);
        person2.setFirstName("Ginette");
        person2.setLastName("Doe");
        person2.setAddress("rue des invisibles");
        person2.setZip("33300");
        person2.setEmail("Ginette.doe@nomail.com");
        person2.setCity("frogcity");
        person2.setPhone("000111222333");

        personsList.add(persons);
        personsList.add(person2);

        when(personRepository.saveAll(personsList)).thenReturn(personsList);

        Iterator<Persons> i = personsList.iterator();

        Assertions.assertEquals(personService.saveAll(personsList).iterator().next(), i.next());
        verify(personRepository, times(1)).saveAll(eq(personsList));

    }

    @Test
    @DisplayName("test save All persons Error")
    void testSaveAllPersonError() {
        when(personRepository.saveAll(null)).thenReturn(null);

        Assertions.assertTrue(personService.saveAll(null) == null);
        verify(personRepository, times(1)).saveAll(eq(null));
    }

    @Test
    @DisplayName("test delete one By Id Success")
    void testDeleteById() {
        Long id = 1L;

        final Persons persons = new Persons();
        persons.setId(1);
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        Optional<Persons> optionalEntityType = Optional.of(persons);

        when(personRepository.findById(id)).thenReturn(optionalEntityType);

        Assertions.assertEquals(personService.deleteById(id).longValue(), persons.getId());
        verify(personRepository, times(1)).deleteById(eq(id));
    }

    @Test
    @DisplayName("test Person findByAddress Success")
    void testPersonFindByAddress() {
        String address = "rue des invisibles";
        List<Persons> personsList = new ArrayList<>();
        Persons persons = new Persons();
        Persons person2 = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");


        person2.setFirstName("Ginette");
        person2.setLastName("Doe");
        person2.setAddress("rue des invisibles");
        person2.setZip("33300");
        person2.setEmail("Ginette.doe@nomail.com");
        person2.setCity("frogcity");
        person2.setPhone("000111222333");

        personsList.add(persons);
        personsList.add(person2);

        when(personRepository.findByAddress(address)).thenReturn(personsList);

        Assertions.assertEquals(personService.findByAddress(address).get(0), personsList.get(0));
        Assertions.assertEquals(personService.findByAddress(address).get(1), personsList.get(1));
        verify(personRepository, times(2)).findByAddress(address);
    }

    @Test
    @DisplayName("test getAllPersons Success")
    void testGetAllPersons() {
        List<Persons> personsList = new ArrayList<>();
        Persons persons = new Persons();
        Persons person2 = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");


        person2.setFirstName("Ginette");
        person2.setLastName("Doe");
        person2.setAddress("rue des invisibles");
        person2.setZip("33300");
        person2.setEmail("Ginette.doe@nomail.com");
        person2.setCity("frogcity");
        person2.setPhone("000111222333");

        personsList.add(persons);
        personsList.add(person2);

        PersonDTO p1 = PersonMapper.convertPersonToPersonDto(persons);
        PersonDTO p2 = PersonMapper.convertPersonToPersonDto(person2);

        List<PersonDTO> personDTOlst = new ArrayList<>();
        personDTOlst.add(p1);
        personDTOlst.add(p2);


        when(personRepository.findAll()).thenReturn(personsList);

        Iterator<PersonDTO> i = personDTOlst.iterator();

        Assertions.assertEquals(personService.getAllPersons().iterator().next(), i.next());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test getAllPersons will return null")
    void testGetAllPersonsWillReturnNull() {
        when(personRepository.findAll()).thenReturn(null);

        Assertions.assertTrue(personService.getAllPersons() == null);
        verify(personRepository, times(1)).findAll();
    }
}
