package com.SafetyNet.Alert.ServiceTest;

import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Dto.Mapper.DtoMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.CommonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CommonServiceTest {

    @Autowired
    private CommonService commonService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    MedicalRecordsRepository medicalRecordsRepository;

    @MockBean
    FirestationRepository firestationRepository;

    @MockBean
    DtoMapper dtoMapper;

    Persons persons = new Persons();
    Persons persons2 = new Persons();
    List<Persons> personsList = new ArrayList<>();
    Firestations firestations = new Firestations();
    Medicalrecords medicalrecords = new Medicalrecords();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        persons.setId(1);
        persons.setFirstName("John");
        persons.setLastName("Doe");
        persons.setAddress("Allée des pins verts");
        persons.setZip("33300");
        persons.setEmail("john.doe@nomail.com");
        persons.setCity("frogcity");
        persons.setPhone("01010101");

        persons2.setId(2);
        persons2.setFirstName("Jack");
        persons2.setLastName("Daniels");
        persons2.setAddress("Allée des pins verts");
        persons2.setZip("33300");
        persons2.setEmail("jack.daniels@nomail.com");
        persons2.setCity("frogcity");
        persons2.setPhone("01020202");

        personsList.add(persons);
        personsList.add(persons2);

        firestations.setAddress("Allée des pins verts");
        firestations.setStation("1");
        firestations.setId(1);

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        medicalrecords.setId(1);
        medicalrecords.setFirstName("John");
        medicalrecords.setLastName("Doe");
        medicalrecords.setBirthdate("08/03/1976");
        medicalrecords.setMedications(medications);
        medicalrecords.setAllergies(allergies);

    }

    @Test
    @DisplayName("Calcul Age Succes")
    public void testCalculAge() throws ParseException {
        String birthdate = LocalDate.now().minusYears(25).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);

        int age = commonService.calculAge(date);

        Assertions.assertEquals(25, age);
    }

    @Test
    @DisplayName("getBirthdate Succes")
    public void testGetBirthdate() throws ParseException {

        when(medicalRecordsRepository.getBirhdate("John", "Doe")).thenReturn("08/03/1976");

        Assertions.assertEquals(commonService.getBirthdate(persons), new SimpleDateFormat("dd/MM/yyyy").parse("08/03/1976"));

        verify(medicalRecordsRepository, times(2)).getBirhdate("John", "Doe");
    }

    @Test
    @DisplayName("getPersons by station number Succes")
    public void testGetPersonsByStationNumber() {

        when(firestationRepository.getPersonsByStationNumber("1")).thenReturn(personsList);

        Assertions.assertEquals(personsList.get(0), commonService.getPersonsByStationNumber("1").get(0));
        Assertions.assertEquals(personsList.get(1), commonService.getPersonsByStationNumber("1").get(1));

        verify(firestationRepository, times(2)).getPersonsByStationNumber("1");
    }

    @Test
    @DisplayName("get Firestation by address Succes")
    public void testGetFirestationByAddress() {
        when(firestationRepository.findFirestationsByAddress("Allée des pins verts")).thenReturn(firestations);
        Assertions.assertEquals(firestations.getStation(), commonService.findByAddress("Allée des pins verts").getStation());

        verify(firestationRepository, times(1)).findFirestationsByAddress("Allée des pins verts");
    }

    @Test
    @DisplayName("find a person medicalRecords Succes")
    public void testFindMedicalRecordsOfAPerson() {
        when(medicalRecordsRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(medicalrecords);

        Assertions.assertEquals("08/03/1976", commonService.findMedicalRecordsOfAPerson(persons).getBirthdate());
        Assertions.assertEquals("John", commonService.findMedicalRecordsOfAPerson(persons).getFirstName());

        verify(medicalRecordsRepository, times(4)).findByfirstNameAndLastName("John", "Doe");
    }

    @Test
    @DisplayName("get phones number list By Station Number Succes")
    public void testGetPhoneNumberListByStationNumber() {
        when(firestationRepository.getPersonsByStationNumber("1")).thenReturn(personsList);

        List<String> phoneNumberList = new ArrayList<>(commonService.getPhoneNumberByStationNumber("1"));
        Assertions.assertEquals(2, phoneNumberList.size());
        Assertions.assertEquals(persons.getPhone(), phoneNumberList.get(1));
        Assertions.assertEquals(persons2.getPhone(), phoneNumberList.get(0));
        verify(firestationRepository, times(1)).getPersonsByStationNumber("1");
    }

    //@PrepareForTest(PersonByAddressMapper.class)
    @Test
    @DisplayName("get persons and FireStation Number by address Succes")
    public void testGetPersonsAndFireStationNumber() throws ParseException {

        List<Persons> personsList = new ArrayList<>();
        personsList.add(persons);

        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setAge(45);
        personsByAddressDTO.setStationNumber("1");
        personsByAddressDTO.setPhone(persons.getPhone());
        personsByAddressDTO.setMedication(medications);
        personsByAddressDTO.setAllergies(allergies);
        personsByAddressDTO.setFirstName(persons.getFirstName());
        personsByAddressDTO.setLastName(persons.getLastName());

        List<PersonsByAddressDTO> personsByAddressDTOS = new ArrayList<>();
        personsByAddressDTOS.add(personsByAddressDTO);

        when(personRepository.findByAddress("Allée des pins verts")).thenReturn(personsList);
        when(firestationRepository.findFirestationsByAddress("Allée des pins verts")).thenReturn(firestations);
        when(medicalRecordsRepository.findByfirstNameAndLastName(persons.getFirstName(), persons.getLastName())).thenReturn(medicalrecords);
        when(dtoMapper.convertPersonToPersonForStationDTO(persons, medicalrecords)).thenReturn(personsByAddressDTO);
        commonService.getPersonsAndFireStationNumber("Allée des pins verts");

        Assertions.assertEquals("John", personsByAddressDTOS.get(0).getFirstName());
        verify(personRepository, times(1)).findByAddress("Allée des pins verts");
        verify(firestationRepository, times(1)).findFirestationsByAddress("Allée des pins verts");
        verify(medicalRecordsRepository, times(2)).findByfirstNameAndLastName("John", "Doe");

    }

    @Test
    @DisplayName("get household by station Number Succes")
    void testHouseholdByStationNumber() throws ParseException {

        List<String> stationNumberList = new ArrayList<>();
        stationNumberList.add("1");

        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setLastName(persons.getLastName());
        personsByAddressDTO.setFirstName(persons.getFirstName());
        personsByAddressDTO.setStationNumber("1");
        personsByAddressDTO.setAge(45);
        personsByAddressDTO.setPhone(persons.getPhone());

        List<Persons> personsList = new ArrayList<>();
        personsList.add(persons);

        HouseholdDTO householdDTO = new HouseholdDTO();
        householdDTO.setStationNumber("1");
        householdDTO.setAddress("Allée des pins verts");

        List<PersonsByAddressDTO> personsByAddressDTOList = new ArrayList<>();
        personsByAddressDTOList.add(personsByAddressDTO);
        householdDTO.setHousehold(personsByAddressDTOList);


        when(firestationRepository.getPersonsByStationNumber(stationNumberList.get(0))).thenReturn(personsList);
        when(personRepository.findByAddress("Allée des pins verts")).thenReturn(personsList);
        when(medicalRecordsRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(medicalrecords);
        when(dtoMapper.convertPersonToPersonForStationDTO(persons, medicalrecords)).thenReturn(personsByAddressDTO);

        List<HouseholdDTO> householdDTOList = commonService.householdByStationNumber(stationNumberList);

        Assertions.assertEquals(1, householdDTOList.size());
        Assertions.assertEquals("John", householdDTOList.get(0).getHousehold().get(0).getFirstName());

        verify(firestationRepository, times(1)).getPersonsByStationNumber("1");
        verify(personRepository, times(1)).findByAddress("Allée des pins verts");
        verify(medicalRecordsRepository, times(2)).findByfirstNameAndLastName("John", "Doe");
        verify(dtoMapper, times(1)).convertPersonToPersonForStationDTO(persons, medicalrecords);
    }

    @Test
    @DisplayName("get persons by station Number Succes")
    void testPersonsByStationNumber() throws ParseException {
        String stationNumber = "1";
        List<Persons> personsList = new ArrayList<>();
        personsList.add(persons);

        PersonForStationDTO personForStationDTO = new PersonForStationDTO();
        personForStationDTO.setPhone(persons.getPhone());
        personForStationDTO.setAddress(persons.getAddress());
        personForStationDTO.setFirstName(persons.getFirstName());
        personForStationDTO.setLastName(persons.getLastName());

        when(firestationRepository.getPersonsByStationNumber(stationNumber)).thenReturn(personsList);
        when(dtoMapper.convertPersonToPersonForStationDTO(persons)).thenReturn(personForStationDTO);

        PersonByStationDTO personByStationDTO = commonService.personsByStationNumber(stationNumber);
        personByStationDTO.addOneAdult();
        personByStationDTO.addOneChildren();
        Assertions.assertEquals(1, personByStationDTO.getPersonsForStationNumber().size());
        Assertions.assertEquals(1, personByStationDTO.getAdultNumber());
        Assertions.assertEquals(1, personByStationDTO.getChildrenNumber());


        verify(firestationRepository, times(1)).getPersonsByStationNumber("1");
        verify(dtoMapper, times(1)).convertPersonToPersonForStationDTO(persons);
    }

    @Test
    @DisplayName("get persons by firstName & firstName Succes")
    void testGetPersonInfoByFirstNameAndLastName() throws ParseException {

        List<Persons> personsList = new ArrayList<>();
        personsList.add(persons);

        PersonInfoDTO personInfoDTO = new PersonInfoDTO();

        personInfoDTO.setAdresse(persons.getAddress());
        personInfoDTO.setMail(persons.getEmail());
        personInfoDTO.setFirstName(persons.getFirstName());
        personInfoDTO.setFirstName(persons.getLastName());
        personInfoDTO.setAllergies(allergies);
        personInfoDTO.setMedication(medications);

        when(personRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(persons);
        when(personRepository.findByAddress("Allée des pins verts")).thenReturn(personsList);
        when(medicalRecordsRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(medicalrecords);
        when(dtoMapper.convertPersonToPersonInfoDto(persons, medicalrecords)).thenReturn(personInfoDTO);

        List<PersonInfoDTO> personInfoDTOS = commonService.getPersonInfoByFirstNameAndLastName("John", "Doe");

        Assertions.assertEquals(1, personInfoDTOS.size());
        verify(personRepository, times(2)).findByfirstNameAndLastName("John", "Doe");
        verify(personRepository, times(1)).findByAddress("Allée des pins verts");
        verify(medicalRecordsRepository, times(2)).findByfirstNameAndLastName("John", "Doe");
        verify(dtoMapper, times(1)).convertPersonToPersonInfoDto(persons, medicalrecords);
    }

    @Test
    @DisplayName("get child by addressSucces")
    void testGetChildByAddress() throws ParseException {

        List<Persons> personsList = new ArrayList<>();
        personsList.add(persons);

        List<FamilyMemberDTO> familyMemberDTOS = new ArrayList<>();
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        familyMemberDTO.setFirstName(persons2.getFirstName());
        familyMemberDTO.setLastName(persons2.getLastName());
        familyMemberDTOS.add(familyMemberDTO);

        ChildrenByAddressDTO childrenByAddressDTO = new ChildrenByAddressDTO();
        childrenByAddressDTO.setFirstName(persons.getFirstName());
        childrenByAddressDTO.setLastName(persons.getLastName());
        childrenByAddressDTO.setAge(10);
        childrenByAddressDTO.setFamillyMembers(familyMemberDTOS);

        when(personRepository.findByAddress("Allée des pins verts")).thenReturn(personsList);
        when(dtoMapper.convertPersonToChildrenByAddressDTO(persons)).thenReturn(childrenByAddressDTO);
        when(dtoMapper.convertPersonToFamillyMemberDTO(persons2)).thenReturn(familyMemberDTO);

        List<ChildrenByAddressDTO> childrenByAddressDTOS = commonService.getChildByAddress("Allée des pins verts");
        Assertions.assertEquals(1, childrenByAddressDTOS.size());
        Assertions.assertEquals("Doe", childrenByAddressDTOS.get(0).getLastName());
        Assertions.assertEquals(1, familyMemberDTOS.size());
        Assertions.assertEquals("Daniels", familyMemberDTOS.get(0).getLastName());

        verify(personRepository, times(2)).findByAddress("Allée des pins verts");
     }

    @Test
    @DisplayName("get get all citizen email Succes")
    void testGetAllCitizenEmail() {
        Set<String> citizenEmailList = new HashSet<>();
        citizenEmailList.add(persons.getEmail());

        when(personRepository.citizenEmail("Bordeaux")).thenReturn(citizenEmailList);

        commonService.getAllCitizenEmail("Bordeaux");

        Assertions.assertEquals(1, citizenEmailList.size());

        verify(personRepository,times(1)).citizenEmail("Bordeaux");
    }


}
