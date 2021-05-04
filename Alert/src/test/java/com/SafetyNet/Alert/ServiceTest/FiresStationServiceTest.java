package com.SafetyNet.Alert.ServiceTest;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Service.FirestationService;
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
class FiresStationServiceTest {


    @Autowired
    FirestationService firestationService;

    @Autowired
    FirestationMapper firestationMapper;

    @MockBean
    FirestationRepository firestationRepository;


    @Test
    @DisplayName("test Find by firstName & lastName Succes")
    void testFindbyFirstNameAndLastName() {

        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        when(firestationRepository.findById(1L)).thenReturn((Optional.of(firestations)));

        Assertions.assertEquals(firestationService.findById(1L).get().getStation(), firestations.getStation());

        verify(firestationRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("test Find by address not exists Succes")
    void testFindbyAddressNotExist() {

        when(firestationRepository.findFirestationsByAddress("mauvaise adresse")).thenReturn(null);

        Assertions.assertTrue(firestationService.findByAddress("mauvaise adresse") == null);
        verify(firestationRepository, times(1)).findFirestationsByAddress("mauvaise adresse");
    }

    @Test
    @DisplayName("test add one FireStation Success")
    void testAddFirestation() {

        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");


        when(firestationRepository.save(firestations)).thenReturn(firestations);

        Assertions.assertEquals(firestations.getId(), firestationService.save(firestations).getId());
        verify(firestationRepository, times(1)).save(eq(firestations));
    }

    @Test
    @DisplayName("test add one FireStation return Error Success")
    void testAddFirestationReturnError() {

        Exception e = new IllegalArgumentException();
        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        when(firestationRepository.save(firestations)).thenThrow(e);

        Firestations f = firestationService.save(firestations);

        Assertions.assertNull(f.getAddress());

        verify(firestationRepository, times(1)).save(eq(firestations));
    }

    @Test
    @DisplayName("test update one Firestation Success")
    void testUpdateFirestation() {
        FirestationDTO firestationDTO = new FirestationDTO();
        firestationDTO.setStation("1");
        firestationDTO.setAddress("rue des Pirates");


        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Long id = 1L;

        when(firestationRepository.findById(id)).thenReturn(Optional.of(firestations));
        when(firestationRepository
                .save(firestationMapper
                        .firestationUpdateDtoToFirestationUpdate(firestationDTO, firestations)))
                .thenReturn(firestationMapper.firestationDTOtoFirestation(firestationDTO));

        Firestations f = firestationService.update(firestationDTO, id);

        Assertions.assertEquals("rue des Pirates", f.getAddress());

        verify(firestationRepository, times(1)).save(eq(firestations));
    }

    @Test
    @DisplayName("test update one Firestation return error Success")
    void testUpdateFirestationReturnError() {
        Exception e = new IllegalArgumentException();

        FirestationDTO firestationDTO = new FirestationDTO();
        firestationDTO.setStation("1");
        firestationDTO.setAddress("rue des Pirates");


        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Long id = 1L;

        when(firestationRepository.findById(id)).thenReturn(Optional.of(firestations));
        when(firestationRepository
                .save(firestationMapper
                        .firestationUpdateDtoToFirestationUpdate(firestationDTO, firestations)))
                .thenThrow(e);

        Firestations f = firestationService.update(firestationDTO, id);

        Assertions.assertNull( f.getAddress());

        verify(firestationRepository, times(1)).save(eq(firestations));
    }

    @Test
    @DisplayName("test add 2 firestation Success")
    void testAdd2Firestation() {
        List<Firestations> firestationsList = new ArrayList<>();

        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Firestations firestations2 = new Firestations();

        firestations2.setId(2);
        firestations2.setStation("1");
        firestations2.setAddress("rue des Geeks");

        firestationsList.add(firestations);
        firestationsList.add(firestations2);

        when(firestationRepository.saveAll(firestationsList)).thenReturn(firestationsList);

        Iterator<Firestations> i = firestationsList.iterator();

        Assertions.assertEquals(i.next(), firestationService.saveAll(firestationsList).iterator().next());
        verify(firestationRepository, times(1)).saveAll(eq(firestationsList));

    }

    @Test
    @DisplayName("test add 2 firestation return Error Success")
    void testAdd2FirestationReturnError() {
        Exception e = new IllegalArgumentException();

        List<Firestations> firestationsList = new ArrayList<>();

        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Firestations firestations2 = new Firestations();

        firestations2.setId(2);
        firestations2.setStation("1");
        firestations2.setAddress("rue des Geeks");

        firestationsList.add(firestations);
        firestationsList.add(firestations2);

        when(firestationRepository.saveAll(firestationsList)).thenThrow(e);

        Assertions.assertNull(firestationService.saveAll(firestationsList));
        verify(firestationRepository, times(1)).saveAll(eq(firestationsList));

    }

    @Test
    @DisplayName("test delete one By Id Success")
    void testDeleteById() {
        Long id = 1L;

        final Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        Optional<Firestations> optionalEntityType = Optional.of(firestations);

        //when(firestationRepository.findById(id)).thenReturn(optionalEntityType);

        Assertions.assertEquals(firestations.getId(), firestationService.deleteById(id).longValue());
        verify(firestationRepository, times(1)).deleteById(eq(id));
    }

    @Test
    @DisplayName("test FindById not exists Success")
    void testFindByIdNotExist() {
        Long id = 1L;

        Exception e = new IllegalArgumentException();
        when(firestationRepository.findById(id)).thenThrow(e);
        Assertions.assertNull(firestationService.findById(id));
        verify(firestationRepository, times(1)).findById(eq(id));
    }



    @Test
    @DisplayName("test Firestation findByAddress Success")
    void testFirestationFindByAddress() {
        String address = "rue des invisibles";
        Firestations firestations = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        when(firestationRepository.findFirestationsByAddress("rue des invisibles")).thenReturn(firestations);

        Assertions.assertEquals(firestations, firestationService.findByAddress(address));
        verify(firestationRepository, times(1)).findFirestationsByAddress(address);
    }

    @Test
    @DisplayName("test Firestation findByAddress return error Success")
    void testFirestationFindByAddressReturnError() {
        Exception e = new IllegalArgumentException();

        String address = "rue des invisibles";

        when(firestationRepository.findFirestationsByAddress("rue des invisibles")).thenThrow(e);

        Assertions.assertNull(firestationService.findByAddress(address));
        verify(firestationRepository, times(1)).findFirestationsByAddress(address);
    }

    @Test
    @DisplayName("test getAllFirestation Success")
    void testGetAllFirestation() {
        List<Firestations> firestationsList = new ArrayList<>();
        Firestations firestations = new Firestations();
        Firestations firestations2 = new Firestations();
        firestations.setId(1);
        firestations.setStation("1");
        firestations.setAddress("rue des invisibles");

        firestations2.setId(2);
        firestations2.setStation("1");
        firestations2.setAddress("rue des Geeks");
        firestationsList.add(firestations);
        firestationsList.add(firestations2);


        when(firestationRepository.findAll()).thenReturn(firestationsList);

        Assertions.assertEquals(firestations, firestationService.findAll().iterator().next());
        verify(firestationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("test getAllFirestation will return null")
    void testGetAllFirestationWillReturnNull() {
        when(firestationRepository.findAll()).thenReturn(null);

        Assertions.assertTrue(firestationService.findAll() == null);
        verify(firestationRepository, times(1)).findAll();
    }
}
