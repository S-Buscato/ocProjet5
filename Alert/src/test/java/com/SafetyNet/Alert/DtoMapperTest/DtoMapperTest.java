package com.SafetyNet.Alert.DtoMapperTest;


import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Dto.Mapper.DtoMapper;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class DtoMapperTest {

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
    @DisplayName("test convert Person to PersonForSationDTO")
    void testConvertPersonToPersonForStationDTO(){

        Persons mockPerson1 = new Persons();
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setPhone("0123456789");
        mockPerson1.setAddress("rue des invisibles");


        PersonForStationDTO personForStationDTO =  dtoMapper.convertPersonToPersonForStationDTO(mockPerson1);

        Assertions.assertEquals("John", personForStationDTO.getFirstName());
        Assertions.assertEquals("Doe", personForStationDTO.getLastName());
    }

    @Test
    @DisplayName("test convert Person to PersonInfoDTO")
    void testConvertPersonToPersonInfoDto(){

        Persons mockPerson1 = new Persons();

        Medicalrecords medicalrecords = new Medicalrecords();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medicalrecords.setId(1);
        medicalrecords.setFirstName("John");
        medicalrecords.setLastName("Doe");
        medicalrecords.setBirthdate("08/03/1976");
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);

        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setEmail("john.doe@nomail.com");

        PersonInfoDTO personInfoDTO =  dtoMapper.convertPersonToPersonInfoDto(mockPerson1, medicalrecords);

        Assertions.assertEquals("John", personInfoDTO.getFirstName());
        Assertions.assertEquals("Doe", personInfoDTO.getLastName());
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
    @DisplayName("test convert medicalrecords to MedicalRecordsDTO")
    void testConvertmedicalrecordsToMedicalrecordsDTO(){

        Medicalrecords medicalrecords = new Medicalrecords();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medicalrecords.setId(1);
        medicalrecords.setFirstName("John");
        medicalrecords.setLastName("Doe");
        medicalrecords.setBirthdate("08/03/1976");
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);

        MedicalRecordsDTO medicalRecordsDTO = MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecords);

        Assertions.assertEquals("John", medicalRecordsDTO.getFirstName());
        Assertions.assertEquals("Doe", medicalRecordsDTO.getLastName());
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

    @Test
    @DisplayName("test convert medicalrecordsUpdateDTO to MedicalRecordsDTO")
    void testConvertmedicalrecordsUpdateDTOToMedicalrecordsDTO(){
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        MedicalRecordsDTO medicalRecordsUpdateDTO = new MedicalRecordsDTO();

        medicalRecordsDTO.setId(1);
        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Doe");
        medicalRecordsDTO.setBirthdate("08/03/1976");
        medicalRecordsDTO.setAllergies(allergies);
        medicalRecordsDTO.setMedications(medications);

        medicalRecordsUpdateDTO.setBirthdate("08/03/1980");
        medicalRecordsUpdateDTO.setMedications(medications);
        medicalRecordsUpdateDTO.setAllergies(allergies);

        medicalRecordsDTO = MedicalrecordsMapper.INSTANCE.convertMedicalRecordsUpdateDtoToMedicalRecordsDTO(medicalRecordsUpdateDTO);

        System.out.println(medicalRecordsDTO);
        Assertions.assertEquals(null, medicalRecordsDTO.getFirstName());
        Assertions.assertEquals(null, medicalRecordsDTO.getLastName());
        Assertions.assertEquals("08/03/1980", medicalRecordsDTO.getBirthdate());

    }

    @Test
    @DisplayName("test convert medicalrecords to MedicalRecordsDTO List")
    void testConvertMedicalrecordsListToMedicalrecordsDTOList(){

        List<Medicalrecords> medicalrecordsList = new ArrayList<>();

        Medicalrecords medicalrecords = new Medicalrecords();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        medicalrecords.setId(1);
        medicalrecords.setFirstName("John");
        medicalrecords.setLastName("Doe");
        medicalrecords.setBirthdate("08/03/1976");
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);

        medicalrecordsList.add(medicalrecords);

        List<MedicalRecordsDTO> medicalRecordsDTOS = MedicalrecordsMapper.INSTANCE.medicalrecordsToMedicalrecordsDTO(medicalrecordsList);

        Assertions.assertEquals(1, medicalRecordsDTOS.size());
        Assertions.assertEquals("Doe", medicalRecordsDTOS.get(0).getLastName());
    }
    @Test
    @DisplayName("test convert medicalrecordsDTO to MedicalRecords")
    void testConvertMedicalrecordsDTOtomedicalrecords(){

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        medicalRecordsDTO.setId(1);
        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Doe");
        medicalRecordsDTO.setBirthdate("08/03/1976");
        medicalRecordsDTO.setMedications(medications);
        medicalRecordsDTO.setAllergies(allergies);

        Medicalrecords medicalrecords = MedicalrecordsMapper.INSTANCE.MedicalrecordsDTOtomedicalrecords(medicalRecordsDTO);

        Assertions.assertEquals("John", medicalrecords.getFirstName());
        Assertions.assertEquals("Doe", medicalrecords.getLastName());
    }



}
