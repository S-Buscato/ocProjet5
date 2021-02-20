package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Model.Person;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(controllers = {PersonController.class, PersonService.class, PersonRepository.class})
@ExtendWith(SpringExtension.class)
class PersonControllerSIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PersonService personServiceMock;

    @MockBean
    private  PersonRepository personRepositoryMock;

    @Test
    public void whenFindByNamethenReturnPersonTest() throws Exception {
        // given
/*
        when(personServiceMock.save(any(Person.class))).thenReturn();
*/

/*
        Person person = new Person(0,"toto", "Coco", "Prof Villemin", "Bordeaux", "33300", "12345678", "toto@mail.com");
*/
        Person person = null;
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}