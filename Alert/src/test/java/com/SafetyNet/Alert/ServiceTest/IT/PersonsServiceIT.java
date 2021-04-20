package com.SafetyNet.Alert.ServiceTest.IT;

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
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class PersonsServiceIT {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

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

        PersonDTO personsSaved = personService.save(persons);
        Assertions.assertEquals(persons.getFirstName(), personsSaved.getFirstName());

        Assertions.assertTrue(personService.findById(persons.getId()).isPresent());
    }

    @Test
    @DisplayName("test Update Person Success")
    void testUpdatePerson() {

        Persons persons = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        PersonDTO personsToUpdate = new PersonDTO();
        personsToUpdate.setFirstName("Jack");
        personsToUpdate.setLastName("Sparrow");
        personsToUpdate.setAddress("rue des inconnus");
        personsToUpdate.setZip("75000");
        personsToUpdate.setEmail("jack.daniels@sea.com");
        personsToUpdate.setCity("blackbird");
        personsToUpdate.setPhone("0102020202");


        personService.save(persons);
        PersonDTO personUpdated = personService.update(personsToUpdate, 1L);

        Assertions.assertEquals(persons.getFirstName(), personUpdated.getFirstName());
        Assertions.assertEquals(personsToUpdate.getCity(), personUpdated.getCity());
        Assertions.assertEquals(persons.getFirstName(), personService.findById(1l).get().getFirstName());
    }

    @Test
    @DisplayName("test delete Person  by FirstName and LastName Success")
    void testDeletePersonByFirstNameAndLastName() {

        Persons persons = new Persons();
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("rue des invisibles");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("000111222333");

        personService.save(persons);

        Long id = personService.deleteOneByfirstnameAndLastname("John", "Doe");

        Assertions.assertEquals(1l, id);

        Assertions.assertFalse(personService.findById(id).isPresent());
    }
}
