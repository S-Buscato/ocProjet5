package com.SafetyNet.Alert.ControllerTest;

import com.SafetyNet.Alert.Dto.Mapper.PersonMapper;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.PersonService;
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
public class PersonsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    public List<PersonDTO> personDTOList = new ArrayList<>();
    public Persons personsMock1 = new Persons();
    public PersonDTO mockPerson1 = new PersonDTO();
    public PersonDTO mockPerson2 = new PersonDTO();

    @BeforeEach
    public void setUp() {

        mockPerson1.setId(1);
        mockPerson1.setFirstName("John");
        mockPerson1.setLastName("Doe");
        mockPerson1.setAddress("rue des invisibles");
        mockPerson1.setZip("33300");
        mockPerson1.setEmail("john.doe@nomail.com");
        mockPerson1.setCity("frogcity");
        mockPerson1.setPhone("000111222333");


        mockPerson2.setId(2);
        mockPerson2.setFirstName("Tarzan");
        mockPerson2.setLastName("Oio");
        mockPerson2.setAddress("rue des inconnus");
        mockPerson2.setZip("33300");
        mockPerson2.setEmail("tarzan.oio@nomail.com");
        mockPerson2.setCity("savaneVille");
        mockPerson2.setPhone("333222111000");

        personDTOList.add(mockPerson1);
        personDTOList.add(mockPerson2);

        personsMock1 = PersonMapper.convertPersonDtoToPerson(mockPerson1);

        when(personService.getAllPersons()).thenReturn(personDTOList);
        when(personService.findByfirstNameAndLastName("John", "Doe")).thenReturn(mockPerson1);
        when(personService.deleteOneByfirstnameAndLastname("John", "Doe")).thenReturn(mockPerson1.getId());
        when(personService.getAllPersons()).thenReturn(personDTOList);
       // when(personService.save(personsMock1)).thenReturn(mockPerson1);
        when(personService.update(mockPerson1, 1L)).thenReturn(mockPerson1);
    }


    @Test
    @DisplayName("test get AllPersons Succes")
    void testGetAllPersons() throws Exception {
        mockMvc.perform(get("/api/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(mockPerson1.getFirstName())))
                .andExpect(jsonPath("$[1].firstName", is(mockPerson2.getFirstName())));
        }

    @Test
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
    }
}
