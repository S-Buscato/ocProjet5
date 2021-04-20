package com.SafetyNet.Alert.ServiceTest.IT;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class MedicalRecordsServiceIT {

    @Autowired
    MedicalrecordsService medicalrecordsService;

    @Autowired
    MedicalRecordsRepository medicalRecordsRepository;

    @Test
    @DisplayName("test add one MedicalRecords Success")
    void testAddMedicalRecords() {

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");


        Medicalrecords medicalRecords = new Medicalrecords();
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Doe");
        medicalRecords.setBirthdate("08/03/1980");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(allergies);

        MedicalRecordsDTO medicalRecordsSaved = medicalrecordsService.save(medicalRecords);
        Assertions.assertEquals(medicalRecords.getFirstName(), medicalRecordsSaved.getFirstName());

        Assertions.assertTrue(medicalrecordsService.findById(medicalRecordsSaved.getId()).isPresent());
    }

    @Test
    @DisplayName("test Update MedicalRecords Success")
    void testUpdateMedicalRecords() {

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");


        Medicalrecords medicalRecords = new Medicalrecords();
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Doe");
        medicalRecords.setBirthdate("08/03/1976");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(allergies);

        MedicalRecordsDTO medicalRecordsToUpdate = new MedicalRecordsDTO();
        medicalRecordsToUpdate.setFirstName("Jack");
        medicalRecordsToUpdate.setLastName("Sparrow");
        medicalRecordsToUpdate.setBirthdate("08/03/1980");
        medicalRecordsToUpdate.setMedications(medications);
        medicalRecordsToUpdate.setAllergies(allergies);



        medicalrecordsService.save(medicalRecords);
        MedicalRecordsDTO medicalRecordsUpdated = medicalrecordsService.update(medicalRecordsToUpdate, 1L);

        Assertions.assertEquals(medicalRecords.getFirstName(), medicalRecordsUpdated.getFirstName());
        Assertions.assertEquals(medicalRecordsToUpdate.getBirthdate(), medicalRecordsUpdated.getBirthdate());
    }

    @Test
    @DisplayName("test delete MedicalRecords by FirstName and LastName Success")
    void testDeleteMedicalRecordsByFirstNameAndLastName() {

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");


        Medicalrecords medicalRecords = new Medicalrecords();
        medicalRecords.setFirstName("John");
        medicalRecords.setLastName("Doe");
        medicalRecords.setBirthdate("08/03/1976");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(allergies);

        medicalrecordsService.save(medicalRecords);

        Long id = medicalrecordsService.deleteOneByfirstnameAndLastname("John", "Doe");

        Assertions.assertEquals(1l, id);

        Assertions.assertFalse(medicalrecordsService.findById(id).isPresent());
    }
}
