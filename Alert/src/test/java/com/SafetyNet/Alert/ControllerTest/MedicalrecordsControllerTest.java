package com.SafetyNet.Alert.ControllerTest;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    MedicalRecordsRepository medicalRecordsRepository;

    public List<Medicalrecords> medicalRecordsList= new ArrayList<>();
    public Medicalrecords medicalrecordsMock1 = new Medicalrecords();
    public Medicalrecords mockMedicalRecordsDTO = new Medicalrecords();
    public MedicalRecordsDTO mockMedicalRecordsDTO2 = new MedicalRecordsDTO();

    @BeforeEach
    public void setUp() {
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        medicalrecordsMock1.setId(1);
        medicalrecordsMock1.setFirstName("John");
        medicalrecordsMock1.setLastName("Doe");
        medicalrecordsMock1.setBirthdate("08/02/1976");
        medicalrecordsMock1.setMedications(medications);
        medicalrecordsMock1.setAllergies(allergies);

        mockMedicalRecordsDTO2.setId(2);
        mockMedicalRecordsDTO2.setFirstName("Tarzan");
        mockMedicalRecordsDTO2.setLastName("Oio");
        mockMedicalRecordsDTO2.setBirthdate("08/02/1976");
        mockMedicalRecordsDTO2.setMedications(medications);
        mockMedicalRecordsDTO2.setAllergies(allergies);


        medicalRecordsList.add(medicalrecordsMock1);

        //medicalrecordsMock1 = MedicalrecordsMapper.INSTANCE.MedicalrecordsDTOtomedicalrecords(medicalrecordsMock1);

        when(medicalrecordsService.findAll()).thenReturn(medicalRecordsList);
        //when(medicalrecordsService.findByfirstNameAndLastName("John", "Doe")).thenReturn(mockPerson1);
        //when(medicalrecordsService.findById(1L)).thenReturn(mockMedicalRecordsDTO2);
        when(medicalrecordsService.save(medicalrecordsMock1)).thenReturn(medicalrecordsMock1);
        //when(medicalrecordsService.update(mockPerson1, 1L)).thenReturn(mockPerson1);
    }


    @Test
    @DisplayName("test get AllMedicalRecords Succes")
    void testGetAllPersons() throws Exception {
        mockMvc.perform(get("/api/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(medicalrecordsMock1.getFirstName())))
                .andExpect(jsonPath("$[0].medications", is(medicalrecordsMock1.getMedications())));
        }

  /*  @Test
    @DisplayName("test delete persons by firstName & lastName Succes")
    void testDeletePersonsByFirstNameAndLastName() throws Exception {

        mockMvc.perform(delete("/api/person/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(result -> mockPerson1.getId());
    }

    @Test
    @DisplayName("test delete persons doesn't exist by firstName & lastName Succes")
    void testDeletePersonsDoesNotExistByFirstNameAndLastName() throws Exception {

        mockMvc.perform(delete("/api/person/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .param("firstName", "Jean")
                .param("lastName", "Bono"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test add persons Succes")
    void testAddPerson() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("firstName","John");
        obj.put("lastName", "Doe");
        obj.put("address", "rue des invisibles");
        obj.put("city", "Bordeaux");
        obj.put("zip", "33000");
        obj.put("phone", "123456789");
        obj.put("email", "john-doe@email.com");

        ObjectMapper o = new ObjectMapper();
        personsMock1 = o.readValue(obj.toJSONString(), Persons.class);
        when(personService.save(personsMock1)).thenReturn(mockPerson1);

        mockMvc.perform(post("/api/person/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(mockPerson1.getFirstName())));


        verify(personRepository, times(0)).save(any(Persons.class));
    }

    @Test
    @DisplayName("test update persons Succes")
    void testUpdatePerson() throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("firstName","Jack");
        obj.put("lastName", "Sparrow");
        obj.put("address", "1509 Culver St");
        obj.put( "city", "frogcity");
        obj.put("zip", "33000");
        obj.put("phone", "123456789");
        obj.put("email", "john-doe@email.com");


        ObjectMapper o = new ObjectMapper();
        personsMock1 = o.readValue(obj.toJSONString(), Persons.class);
        when(personService.save(personsMock1)).thenReturn(mockPerson1);


        mockMvc.perform(put("/api/person/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(obj.toJSONString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                //.andExpect(jsonPath("$.firstName", is(mockPerson1.getFirstName())))
                //.andExpect(jsonPath("$.id", is(mockPerson1.getId())))
        ;
    }*/
}
