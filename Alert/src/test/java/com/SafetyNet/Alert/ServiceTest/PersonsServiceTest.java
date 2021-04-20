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
    @DisplayName("test Persons Find by id not exists Succes")
    void testPersonsFindById() {
        Persons persons = new Persons();
        persons.setId(1);
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");
        when(personRepository.findById(1L)).thenReturn(Optional.of(persons));

        Assertions.assertNotNull(personService.findById(1L));
        verify(personRepository, times(1)).findById(1L);
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

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(1);
        personDTO.setFirstName("John");
        personDTO.setLastName("Doe");
        personDTO.setAddress("rue des invisibles");
        personDTO.setZip("33300");
        personDTO.setEmail("john.doe@nomail.com");
        personDTO.setCity("frogcity");
        personDTO.setPhone("000111222333");

        Persons persons2 = new Persons();
        persons2.setId(1);
        persons2.setFirstName("John");
        persons2.setLastName("Doe");
        persons2.setAddress("Bvd des Caraïbes");
        persons2.setZip("33300");
        persons2.setEmail("jack.D@nomail.com");
        persons2.setCity("Blackbird");
        persons2.setPhone("0102020202");

        Long id = 1L;


        when(personRepository.findById(id)).thenReturn(Optional.of(persons2));
        when(personRepository.save(PersonMapper.convertPersonDtoToPerson(personDTO))).thenReturn(persons2);


        PersonDTO p = personService.update(personDTO, id);
        Assertions.assertEquals("John", p.getFirstName());
        Assertions.assertEquals("Doe", p.getLastName());
        Assertions.assertEquals("Blackbird", p.getCity());

        verify(personRepository, times(1)).save(personMapper.convertPersonDtoToPerson(personDTO));
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
    @DisplayName("test delete one By LastName and Firstname")
    void testDeleteByFirstNameAndLastName() {
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

        when(personRepository.findByfirstNameAndLastName("John","Doe")).thenReturn(persons);

        when(personRepository.findById(id)).thenReturn(optionalEntityType);

        Assertions.assertEquals(personService.deleteOneByfirstnameAndLastname("John","Doe").longValue(), persons.getId());
        verify(personRepository, times(1)).deleteById(id);
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

        Assertions.assertEquals(i.next().getLastName(), personService.getAllPersons().iterator().next().getLastName());
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
