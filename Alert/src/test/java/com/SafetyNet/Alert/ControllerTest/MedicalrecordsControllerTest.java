package com.SafetyNet.Alert.ControllerTest;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class MedicalrecordsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MedicalrecordsService medicalrecordsService;


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
    @DisplayName("test get AllMedicalRecords Succes")
    void testGetAllMedicalRecords() throws Exception {

        List<Medicalrecords> medicalrecordsList = new ArrayList<>();
        medicalrecordsList.add(medicalrecordsMock1);

        when(medicalrecordsService.findAll()).thenReturn(medicalrecordsList);

        mockMvc.perform(get("/api/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(medicalrecordsMock1.getFirstName())))
                .andExpect(jsonPath("$[0].medications", is(medicalrecordsMock1.getMedications())));

        verify(medicalrecordsService,times(1)).findAll();
        }

    @Test
    @DisplayName("test get AllMedicalRecords Return Error Succes")
    void testGetAllMedicalRecordsReturnError() throws Exception {

        List<Medicalrecords> medicalrecordsList = new ArrayList<>();
        medicalrecordsList.add(medicalrecordsMock1);

        Exception e = new IllegalArgumentException();

        when(medicalrecordsService.findAll()).thenThrow(e);

        mockMvc.perform(get("/api/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService,times(1)).findAll();
        }


    @Test
    @DisplayName("test delete medicalrecords by firstName & lastName Succes")
    void testDeleteMedicalRecordsByFirstNameAndLastName() throws Exception {

        when(medicalrecordsService.deleteOneByfirstnameAndLastname("John","Doe")).thenReturn(medicalrecordsMock1.getId());

        mockMvc.perform(delete("/api/medicalrecords/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .param("firstname", "John")
                .param("lastname", "Doe"))
                .andExpect(status().isOk())
                .andExpect(result -> medicalrecordsMock1.getId());

        verify(medicalrecordsService, times(1)).deleteOneByfirstnameAndLastname("John", "Doe");
    }

    @Test
    @DisplayName("test delete medicalrecords by firstName & lastName return Error Succes")
    void testDeleteMedicalRecordsByFirstNameAndLastNameReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        when(medicalrecordsService.deleteOneByfirstnameAndLastname("John","Doe")).thenThrow(e);

        mockMvc.perform(delete("/api/medicalrecords/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .param("firstname", "John")
                .param("lastname", "Doe"))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService, times(1)).deleteOneByfirstnameAndLastname("John", "Doe");
    }

    @Test
    @DisplayName("test medicalrecords findById Succes")
    void testMedicalRecordsFindById() throws Exception {

        Long id = 1L;
        when(medicalrecordsService.findById(id)).thenReturn(Optional.of(medicalrecordsMock1));

        mockMvc.perform(get("/api/medicalrecords/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(medicalrecordsMock1.getFirstName())));

        verify(medicalrecordsService, times(2)).findById(id);
    }

    @Test
    @DisplayName("test medicalrecords findById return Error Succes")
    void testMedicalRecordsFindByIdReturnError() throws Exception {

        Long id = 1L;
        Exception e = new IllegalArgumentException();

        when(medicalrecordsService.findById(id)).thenThrow(e);

        mockMvc.perform(get("/api/medicalrecords/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService, times(1)).findById(id);
    }

    @Test
    @DisplayName("test delete medicalRecords doesn't exist by firstName & lastName Succes")
    void testDeleteMedicalRecordsDoesNotExistByFirstNameAndLastName() throws Exception {

        Exception e = new IllegalArgumentException();

        when(medicalrecordsService.deleteOneByfirstnameAndLastname("Jean","Bono")).thenThrow(e);

        mockMvc.perform(delete("/api/medicalrecords/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .param("firstname", "Jean")
                .param("lastname", "Bono"))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService, times(1)).deleteOneByfirstnameAndLastname("Jean", "Bono");
    }


    @Test
    @DisplayName("test add medicalrecords Succes")
    void testAddMedicalRecord() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("firstName","John");
        obj.put("lastName", "Doe");
        obj.put("birthdate", "08/03/1976");
        obj.put("medications", medications);
        obj.put("allergies", allergies);

        ObjectMapper o = new ObjectMapper();
        mockMedicalRecordsDTO = o.readValue(obj.toJSONString(), MedicalRecordsDTO.class);

        when(medicalrecordsService.save(medicalrecordsMock1)).thenReturn(mockMedicalRecordsDTO);

        mockMvc.perform(post("/api/medicalrecords/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.firstName", is(medicalrecordsMock1.getFirstName())));

        verify(medicalrecordsService, times(1)).save(any(Medicalrecords.class));
    }

    @Test
    @DisplayName("test add medicalrecords return Error Succes")
    void testAddMedicalRecordReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        JSONObject obj = new JSONObject();
        obj.put("firstName","John");
        obj.put("lastName", "Doe");
        obj.put("birthdate", "08/03/1976");
        obj.put("medications", medications);
        obj.put("allergies", allergies);

        when(medicalrecordsService.save(any(Medicalrecords.class))).thenThrow(e);

        mockMvc.perform(post("/api/medicalrecords/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService, times(1)).save(any(Medicalrecords.class));
    }


    @Test
    @DisplayName("test update medicalRecords Succes")
    void testUpdatePerson() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("firstName","John");
        obj.put("lastName", "Doe");
        obj.put("birthdate", "08/03/1976");
        obj.put("medications", medications);
        obj.put("allergies", allergies);

        ObjectMapper o = new ObjectMapper();
        MedicalRecordsDTO medicalRecordsDTO = o.readValue(obj.toJSONString(), MedicalRecordsDTO.class);

        when(medicalrecordsService.update(any(MedicalRecordsDTO.class), anyLong())).thenReturn(medicalRecordsDTO);

        mockMvc.perform(put("/api/medicalrecords/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        verify(medicalrecordsService, times(1)).update(any(MedicalRecordsDTO.class), anyLong());
    }

    @Test
    @DisplayName("test update medicalRecords Return Error Succes")
    void testUpdateMedicalRecordsReturnError() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("firstName","John");
        obj.put("lastName", "Doe");
        obj.put("birthdate", "08/03/1976");
        obj.put("medications", medications);
        obj.put("allergies", allergies);

        ObjectMapper o = new ObjectMapper();
        MedicalRecordsDTO medicalRecordsDTO = o.readValue(obj.toJSONString(), MedicalRecordsDTO.class);

        Exception e = new IllegalArgumentException();

        when(medicalrecordsService.update(any(MedicalRecordsDTO.class), anyLong())).thenThrow(e);

        mockMvc.perform(put("/api/medicalrecords/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(medicalrecordsService, times(1)).update(any(MedicalRecordsDTO.class), anyLong());
    }
}
