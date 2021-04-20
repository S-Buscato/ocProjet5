package com.SafetyNet.Alert.ServiceTest;

import com.SafetyNet.Alert.Dto.Mapper.MedicalrecordsMapper;
import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class MedicalrecordsServiceTest {


    @Autowired
    MedicalrecordsService medicalrecordsService;

    @Autowired
    MedicalrecordsMapper medicalrecordsMapper;

    @MockBean
    MedicalRecordsRepository medicalRecordsRepository;

    public List<Medicalrecords> medicalRecordsList= new ArrayList<>();
    public Medicalrecords medicalrecordsMock1 = new Medicalrecords();
    public MedicalRecordsDTO mockMedicalRecordsDTO = new MedicalRecordsDTO();
    List<String> medications = new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        medicalrecordsMock1.setId(1);
        medicalrecordsMock1.setFirstName("John");
        medicalrecordsMock1.setLastName("Doe");
        medicalrecordsMock1.setBirthdate("08/03/1976");
        medicalrecordsMock1.setMedications(medications);
        medicalrecordsMock1.setAllergies(allergies);

        mockMedicalRecordsDTO.setId(2);
        mockMedicalRecordsDTO.setFirstName("Jack");
        mockMedicalRecordsDTO.setLastName("Sparrow");
        mockMedicalRecordsDTO.setBirthdate("08/03/1976");
        mockMedicalRecordsDTO.setMedications(medications);
        mockMedicalRecordsDTO.setAllergies(allergies);


        medicalRecordsList.add(medicalrecordsMock1);
    }


    @Test
    @DisplayName("test Find by firstName & lastName Succes")
    void testFindbyFirstNameAndLastName() {

        when(medicalRecordsRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(medicalrecordsMock1);

        Assertions.assertEquals(medicalrecordsMock1.getFirstName(), medicalrecordsService.findByfirstnameAndLastname("John", "Doe").getFirstName());
        verify(medicalRecordsRepository, times(1)).findByfirstNameAndLastName("John", "Doe");
    }


    @Test
    @DisplayName("test Find by firstName & lastName not exists Succes")
    void testFindbyFirstNameAndLastNameNotExist() {

        when(medicalRecordsRepository.findByfirstNameAndLastName("John", "Doe")).thenReturn(null);

        Assertions.assertNull(medicalrecordsService.findByfirstnameAndLastname("John", "Doe") );
        verify(medicalRecordsRepository, times(1)).findByfirstNameAndLastName("John", "Doe");
    }

    @Test
    @DisplayName("test MedicalRecords FindById")
    void testMedicalRecordsFindbyId() {

        Long id = 1L;

        when(medicalRecordsRepository.findById(id)).thenReturn(Optional.of(medicalrecordsMock1));

        Assertions.assertEquals(medicalrecordsMock1, medicalrecordsService.findById(id).get());
        verify(medicalRecordsRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("test MedicalRecords FindById does not exist error")
    void testMedicalRecordsFindbyIdDoesNotExist() {

        Long id = 15L;

        when(medicalRecordsRepository.findById(id)).thenReturn(null);

        Assertions.assertNull(medicalrecordsService.findById(id));
        verify(medicalRecordsRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("test add one MedicalRecords Success")
    void testAddMedicalRecords() {
        when(medicalRecordsRepository.save(medicalrecordsMock1)).thenReturn(medicalrecordsMock1);

        Assertions.assertEquals(medicalrecordsMock1.getFirstName(), medicalrecordsService.save(medicalrecordsMock1).getFirstName());
        verify(medicalRecordsRepository, times(1)).save(eq(medicalrecordsMock1));
    }

    @Test
    @DisplayName("test update one Medicalrecords Success")
    void testUpdateMedicalRecords() {

        MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setId(1);
        medicalRecordsDTO.setFirstName("John");
        medicalRecordsDTO.setLastName("Doe");
        medicalRecordsDTO.setBirthdate("08/03/1976");
        medicalRecordsDTO.setMedications(medications);
        medicalRecordsDTO.setAllergies(allergies);

        Medicalrecords medicalRecords = new Medicalrecords();
        medicalRecords.setId(1);
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Doe");
        medicalRecords.setBirthdate("08/03/1980");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(allergies);

           Long id = 1L;


        when(medicalRecordsRepository.findById(id)).thenReturn(Optional.of(medicalRecords));
        when(medicalRecordsRepository.save(medicalrecordsMapper.MedicalrecordsDTOtomedicalrecords(medicalRecordsDTO))).thenReturn(medicalRecords);


        MedicalRecordsDTO m = medicalrecordsService.update(medicalRecordsDTO, id);
        Assertions.assertEquals("John", m.getFirstName());
        Assertions.assertEquals("Doe", m.getLastName());

        verify(medicalRecordsRepository, times(1)).save(medicalrecordsMapper.MedicalrecordsDTOtomedicalrecords(medicalRecordsDTO));
    }

    @Test
    @DisplayName("test add 2 MedicalRecords Success")
    void testAdd2MedicalRecords() {
        List<Medicalrecords> medicalrecordsList = new ArrayList<>();

        Medicalrecords medicalrecordsMock2 = new Medicalrecords();
        medicalrecordsMock2.setId(2);
        medicalrecordsMock2.setFirstName("Jack");
        medicalrecordsMock2.setLastName("Sparrow");
        medicalrecordsMock2.setBirthdate("08/03/1976");
        medicalrecordsMock2.setMedications(medications);
        medicalrecordsMock2.setAllergies(allergies);

        medicalrecordsList.add(medicalrecordsMock1);
        medicalrecordsList.add(medicalrecordsMock2);

        when(medicalRecordsRepository.saveAll(medicalrecordsList)).thenReturn(medicalrecordsList);

        Iterator<Medicalrecords> i = medicalrecordsList.iterator();

        Assertions.assertEquals(i.next(), medicalrecordsService.saveAll(medicalrecordsList).iterator().next());
        verify(medicalRecordsRepository, times(1)).saveAll(eq(medicalrecordsList));

    }

    @Test
    @DisplayName("test save All medicalRecords Error")
    void testSaveAllPersonError() {
        when(medicalRecordsRepository.saveAll(null)).thenReturn(null);

        Assertions.assertNull(medicalrecordsService.saveAll(null));
        verify(medicalRecordsRepository, times(1)).saveAll(eq(null));
    }

    @Test
    @DisplayName("test delete one By Id Success")
    void testDeleteById() {
        Long id = 1L;

        Optional<Medicalrecords> optionalEntityType = Optional.of(medicalrecordsMock1);

        when(medicalRecordsRepository.findById(id)).thenReturn(optionalEntityType);

        Assertions.assertEquals(medicalrecordsService.deleteById(id).longValue(), medicalrecordsMock1.getId());
        verify(medicalRecordsRepository, times(1)).deleteById(eq(id));
    }

    @Test
    @DisplayName("test delete one By FirstName and LastName")
    void testDeleteByFirstNameAndLastName() {
        Long id = 1L;

        Optional<Medicalrecords> optionalEntityType = Optional.of(medicalrecordsMock1);

        when(medicalRecordsRepository.findById(id)).thenReturn(optionalEntityType);
        when(medicalRecordsRepository.findByfirstNameAndLastName("John","Doe")).thenReturn(medicalrecordsMock1);

        Assertions.assertEquals(medicalrecordsMock1.getId(), medicalrecordsService.deleteOneByfirstnameAndLastname("John", "Doe").longValue());
        verify(medicalRecordsRepository, times(1)).deleteById(eq(id));
    }



    @Test
    @DisplayName("test getAllMedicalRecords Success")
    void testGetAllMedicalRecords() {
        List<Medicalrecords> medicalrecordsList = new ArrayList<>();

        Medicalrecords medicalrecordsMock1 = new Medicalrecords();

        medicalrecordsMock1.setId(1);
        medicalrecordsMock1.setFirstName("John");
        medicalrecordsMock1.setLastName("Doe");
        medicalrecordsMock1.setBirthdate("08/03/1976");
        medicalrecordsMock1.setMedications(medications);
        medicalrecordsMock1.setAllergies(allergies);

        Medicalrecords medicalrecordsMock2 = new Medicalrecords();
        medicalrecordsMock2.setId(2);
        medicalrecordsMock2.setFirstName("Jack");
        medicalrecordsMock2.setLastName("Sparrow");
        medicalrecordsMock2.setBirthdate("08/03/1976");
        medicalrecordsMock2.setMedications(medications);
        medicalrecordsMock2.setAllergies(allergies);


        medicalrecordsList.add(medicalrecordsMock1);
        medicalrecordsList.add(medicalrecordsMock2);

        when(medicalRecordsRepository.findAll()).thenReturn(medicalrecordsList);

        Assertions.assertEquals(medicalrecordsService.findAll().iterator().next(), medicalrecordsMock1);
        verify(medicalRecordsRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test getAllMedicalRecords will return null")
    void testGetAllMedicalRecordsWillReturnNull() {
        when(medicalRecordsRepository.findAll()).thenReturn(null);

        Assertions.assertNull(medicalrecordsService.findAll());
        verify(medicalRecordsRepository, times(1)).findAll();
    }
}
