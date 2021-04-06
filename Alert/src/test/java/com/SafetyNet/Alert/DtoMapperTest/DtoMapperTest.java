package com.SafetyNet.Alert.DtoMapperTest;


import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Dto.Mapper.*;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class DtoMapperTest {

    @Autowired
    PersonByAddressMapper personByAddressMapper;

    @Autowired
    DtoMapper dtoMapper;


    @BeforeEach
    public void setUp() {

        List<Persons> personsList = new ArrayList<>();
        Persons mockPerson1 = new Persons();
        mockPerson1.setId(1);
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setZip("33300");
        mockPerson1.setEmail("john.doe@nomail.com");
        mockPerson1.setCity("frogcity");
        mockPerson1.setPhone("000111222333");
        personsList.add(mockPerson1);

        Persons mockPerson2 = new Persons();
        mockPerson2.setId(2);
        mockPerson2.setFirstName("Tarzan");
        mockPerson2.setLastName("Oio");
        mockPerson2.setAddress("rue des inconnus");
        mockPerson2.setZip("33300");
        mockPerson2.setEmail("tarzan.oio@nomail.com");
        mockPerson2.setCity("savaneVille");
        mockPerson2.setPhone("333222111000");
        personsList.add(mockPerson2);

    }

    @Test
    @DisplayName("test convert Person to ChildrenDtoByAddressDTO")
    void testConvertPersonToChildrenByAddressDto(){

        Persons mockPerson1 = new Persons();
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");

        ChildrenByAddressDTO childrenByAddressDTO =  dtoMapper.convertPersonToChildrenByAddressDTO(mockPerson1);
        Assertions.assertEquals("John", childrenByAddressDTO.getFirstName());
        Assertions.assertEquals("Doe", childrenByAddressDTO.getLastName());
    }

    @Test
    @DisplayName("test convert Person to FamilyMemberDTO")
    void testConvertPersonToFamilyMemberDto(){

        Persons mockPerson1 = new Persons();
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");

        FamilyMemberDTO childrenByAddressDTO =  dtoMapper.convertPersonToFamillyMemberDTO(mockPerson1);
        Assertions.assertEquals("John", childrenByAddressDTO.getFirstName());
        Assertions.assertEquals("Doe", childrenByAddressDTO.getLastName());
    }

    @Test
    @DisplayName("test convert Person to PersonByAddressDTO")
    void testConvertPersonToPersonByAddressDto(){

        Persons mockPerson1 = new Persons();
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setPhone("0123456789");

        List<String> medications = new ArrayList<String>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("nillacilan");

        Medicalrecords mockMedicalrecords = new Medicalrecords();
        mockMedicalrecords.setMedications(medications);
        mockMedicalrecords.setAllergies(allergies);

        PersonsByAddressDTO personsByAddressDTO =  dtoMapper.convertPersonToPersonForStationDTO(mockPerson1, mockMedicalrecords);
        Assertions.assertEquals("John", personsByAddressDTO.getFirstName());
        Assertions.assertEquals("Doe", personsByAddressDTO.getLastName());
        Assertions.assertEquals(2, personsByAddressDTO.getMedication().size());
        Assertions.assertEquals("nillacilan", personsByAddressDTO.getAllergies().get(0));
    }

    @Test
    @DisplayName("test convert Person to PersonDTO")
    void testConvertPersonToPersonDto(){

        Persons mockPerson1 = new Persons();
        mockPerson1.setId(1);
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setZip("33300");
        mockPerson1.setEmail("john.doe@nomail.com");
        mockPerson1.setCity("frogcity");
        mockPerson1.setPhone("000111222333");

        PersonDTO personDTO =  PersonMapper.convertPersonToPersonDto(mockPerson1);
        Assertions.assertEquals("John", personDTO.getFirstName());
        Assertions.assertEquals("Doe", personDTO.getLastName());
    }

    @Test
    @DisplayName("test convert PersonDTO to Person")
    void testConvertPersonDtoToPersons(){

        PersonDTO mockPerson1 = new PersonDTO();
        mockPerson1.setId(1);
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setZip("33300");
        mockPerson1.setEmail("john.doe@nomail.com");
        mockPerson1.setCity("frogcity");
        mockPerson1.setPhone("000111222333");

        Persons persons =  PersonMapper.convertPersonDtoToPerson(mockPerson1);
        Assertions.assertEquals("John", persons.getFirstName());
        Assertions.assertEquals("Doe", persons.getLastName());
    }

    @Test
    @DisplayName("test convert PersonDUpdateDTO to PersonDTO")
    void testConvertPersonUpdateDtoToPersonDto(){

        PersonDTO mockPerson1 = new PersonDTO();
        mockPerson1.setId(1);
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setZip("33300");
        mockPerson1.setEmail("john.doe@nomail.com");
        mockPerson1.setCity("frogcity");
        mockPerson1.setPhone("000111222333");

        PersonDTO mockPerson2 = new PersonDTO();
        mockPerson2.setId(2);
        mockPerson2.setFirstName("Tarzan");
        mockPerson2.setLastName("Oio");
        mockPerson2.setAddress("rue des inconnus");
        mockPerson2.setZip("33300");
        mockPerson2.setEmail("tarzan.oio@nomail.com");
        mockPerson2.setCity("savaneVille");
        mockPerson2.setPhone("333222111000");

        mockPerson2 = PersonMapper.convertPersonUpdateDtoToPersonDto(mockPerson1);
        Assertions.assertTrue(mockPerson2.getFirstName() == null);
        Assertions.assertTrue( mockPerson2.getLastName() == null);
        Assertions.assertEquals("rue des invisibles", mockPerson2.getAddress());
        Assertions.assertEquals("frogcity", mockPerson2.getCity());
    }

    @Test
    @DisplayName("test convert Firestation to FirestationDTO")
    void testConvertFirestationToFirestationDTO(){
        Firestations firestations = new Firestations();
        firestations.setStation("5");
        firestations.setAddress("rue d'Ornano Bordeaux");

        FirestationDTO firestationDTO = FirestationMapper.INSTANCE.firestationToFirestationDTO(firestations);

        Assertions.assertEquals("5", firestationDTO.getStation());
    }

    @Test
    @DisplayName("test convert FirestationDTO to Firestation")
    void testConvertFirestationDTOtoFirestation(){
        FirestationDTO firestationDTO = new FirestationDTO();
        firestationDTO.setStation("5");
        firestationDTO.setAddress("rue d'Ornano Bordeaux");

        Firestations firestations = FirestationMapper.INSTANCE.firestationDTOtoFirestation(firestationDTO);

        Assertions.assertEquals("5", firestations.getStation());
    }

    @Test
    @DisplayName("test convert Firestation to FirestationDTO List")
    void testConvertFirestationToFirestationDTOList(){
        Firestations firestations1 = new Firestations();
        firestations1.setStation("5");
        firestations1.setAddress("rue d'Ornano Bordeaux");

        Firestations firestations2 = new Firestations();
        firestations1.setStation("6");
        firestations1.setAddress("rue de la Benauge Bordeaux");

        List<Firestations> firestationsList = new ArrayList<>();
        firestationsList.add(firestations1);
        firestationsList.add(firestations2);

        List<FirestationDTO> firestationDTOList = FirestationMapper.INSTANCE.firestationToFirestationsDTO(firestationsList);

        Assertions.assertEquals(2, firestationDTOList.size());
    }

}
