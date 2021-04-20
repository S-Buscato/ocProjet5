package com.SafetyNet.Alert.ServiceTest.IT;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Service.FirestationService;
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
public class FireStationServiceIT {

    @Autowired
    FirestationService firestationService;

    @Autowired
    FirestationRepository firestationRepository;

    @Test
    @DisplayName("test add one Firestation Success")
    void testAddFireStation() {

        Firestations firestations = new Firestations();
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Firestations firestationSaved = firestationService.save(firestations);
        Assertions.assertEquals(firestations.getAddress(), firestationSaved.getAddress());

        Assertions.assertTrue(firestationService.findById(firestationSaved.getId()).isPresent());
    }

    @Test
    @DisplayName("test Update FireSation Success")
    void testUpdateFirestation() {

        Firestations firestations = new Firestations();
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        FirestationDTO firestationToUpdate = new FirestationDTO();
        firestationToUpdate.setStation("2");
        firestationToUpdate.setAddress("rue des inconus");


        firestationService.save(firestations);
        Firestations firestationUpdated = firestationService.update(firestationToUpdate, 1L);

        Assertions.assertEquals(firestationToUpdate.getStation(), firestationUpdated.getStation());
        Assertions.assertEquals(firestationToUpdate.getAddress(), firestationUpdated.getAddress());
    }

    @Test
    @DisplayName("test delete Firestation Success")
    void testDeleteFirestation() {

        Firestations firestations = new Firestations();
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        firestationService.save(firestations);

        Long id = firestationService.deleteById(1L);

        Assertions.assertEquals(1l, id);

        Assertions.assertFalse(firestationService.findById(id).isPresent());
    }
}
