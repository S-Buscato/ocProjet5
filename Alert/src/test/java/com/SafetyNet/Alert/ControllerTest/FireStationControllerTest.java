package com.SafetyNet.Alert.ControllerTest;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Dto.Mapper.FirestationMapper;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Service.FirestationService;
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
public class FireStationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FirestationService firestationService;


    public List<FirestationDTO> firestationDTOS = new ArrayList<>();
    public List<Firestations> firestationsList = new ArrayList<>();

    public Firestations firestationsMock1 = new Firestations();
    public FirestationDTO firestationDTO1 = new FirestationDTO();
    public FirestationDTO firestationDTO2 = new FirestationDTO();

    @BeforeEach
    public void setUp() {

        firestationDTO1.setId(1);
        firestationDTO1.setStation("1");
        firestationDTO1.setAddress("rue des invisibles");

        firestationDTO2.setId(2);
        firestationDTO2.setStation("1");
        firestationDTO2.setAddress("rue des Geeks");

        firestationDTOS.add(firestationDTO1);
        firestationDTOS.add(firestationDTO2);

        firestationsMock1 = FirestationMapper.INSTANCE.firestationDTOtoFirestation(firestationDTO1);
        firestationsList.add(firestationsMock1);

    }


    @Test
    @DisplayName("test get AllStation Succes")
    void testGetAllFirestations() throws Exception {
        when(firestationService.findAll()).thenReturn(firestationsList);

        mockMvc.perform(get("/api/firestations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].station", is(firestationDTO1.getStation())));

        verify(firestationService, times(1)).findAll();
    }

    @Test
    @DisplayName("test get AllStation Return Error Succes")

    void testGetAllFirestationsReturnError() throws Exception {
        Exception e = new IllegalArgumentException();
        when(firestationService.findAll()).thenThrow(e);

        mockMvc.perform(get("/api/firestations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(firestationService, times(1)).findAll();
    }

    @Test
    @DisplayName("test get station by id Succes")
    void testGetFirestationById() throws Exception {
        when(firestationService.findById(1L)).thenReturn(java.util.Optional.ofNullable(firestationsMock1));

        mockMvc.perform(get("/api/firestation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station", is(firestationDTO1.getStation())));

        verify(firestationService, times(2)).findById(anyLong());
    }

    @Test
    @DisplayName("test get station by id return error Succes")
    void testGetFirestationByIdReturnError() throws Exception {
        Exception e = new IllegalArgumentException();

        when(firestationService.findById(1L)).thenThrow(e);

        mockMvc.perform(get("/api/firestation/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(firestationService, times(1)).findById(anyLong());

    }

    @Test
    @DisplayName("test delete firestation by id Succes")
    void testDeleteFirestationById() throws Exception {
        when(firestationService.deleteById(1L)).thenReturn(firestationDTO1.getId());

        mockMvc.perform(delete("/api/firestation/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(result -> firestationDTO1.getId());

        verify(firestationService, times(1)).deleteById(anyLong());

    }

    @Test
    @DisplayName("test delete firestation by id return error Succes")
    void testDeleteFirestationByIdReturnError() throws Exception {
        Exception e = new IllegalArgumentException();
        when(firestationService.deleteById(1L)).thenThrow(e);

        mockMvc.perform(delete("/api/firestation/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(status().is5xxServerError());

        verify(firestationService, times(1)).deleteById(anyLong());

    }

    @Test
    @DisplayName("test delete Firestation doesn't exist by id Succes")
    void testDeleteFiresStationDoesNotExistById() throws Exception {

        when(firestationService.deleteById(1L)).thenReturn(firestationDTO1.getId());

        mockMvc.perform(delete("/api/firestation/delete/2")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", is("Station inexistante")));

        verify(firestationService, times(1)).deleteById(anyLong());

    }

    @Test
    @DisplayName("test add Firestation Succes")
    void testAddFirestation() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("station","15");
        obj.put("address", "1509 Culver St");

        ObjectMapper o = new ObjectMapper();
        firestationsMock1 = o.readValue(obj.toJSONString(), Firestations.class);

        when(firestationService.save(firestationsMock1)).thenReturn(firestationsMock1);

        mockMvc.perform(post("/api/firestation/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.station", is(firestationsMock1.getStation())));

        verify(firestationService, times(1)).save(any(Firestations.class));
    }
    @Test
    @DisplayName("test add Firestation return error Succes")
    void testAddFirestationReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        JSONObject obj = new JSONObject();
        obj.put("station","15");
        obj.put("address", "1509 Culver St");

        ObjectMapper o = new ObjectMapper();
        firestationsMock1 = o.readValue(obj.toJSONString(), Firestations.class);

        when(firestationService.save(firestationsMock1)).thenThrow(e);

        mockMvc.perform(post("/api/firestation/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(firestationService, times(1)).save(any(Firestations.class));
    }

    @Test
    @DisplayName("test update Firestation Succes")
    void testUpdateFirestation() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("station","2");
        obj.put("address", "1509 Culver Street");

        ObjectMapper o = new ObjectMapper();
        FirestationDTO fdto = o.readValue(obj.toJSONString(), FirestationDTO.class);
        fdto.setId(1);

        when(firestationService.update(fdto, 1L)).thenReturn(firestationsMock1);

        mockMvc.perform(put("/api/firestation/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("test update Firestation return errorSucces")
    void testUpdateFirestationReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        JSONObject obj = new JSONObject();
        obj.put("station","2");
        obj.put("address", "1509 Culver Street");

        ObjectMapper o = new ObjectMapper();
        FirestationDTO fdto = o.readValue(obj.toJSONString(), FirestationDTO.class);
        fdto.setId(1);

        when(firestationService.update(any(FirestationDTO.class), anyLong())).thenThrow(e);

        mockMvc.perform(put("/api/firestation/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }
}
