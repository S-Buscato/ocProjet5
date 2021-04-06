package com.SafetyNet.Alert.ControllerTest;


import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class CommonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CommonService commonService;

    List<ChildrenByAddressDTO> childrenByAddressDTOS = new ArrayList<>();

    PersonByStationDTO personByStationDTO = new PersonByStationDTO();
    List<PersonInfoDTO> personInfoDTO = new ArrayList<>();
    List<HouseholdDTO> householdDTO = new ArrayList<>();
    List<PersonsByAddressDTO> personsByAddressDTO = new ArrayList<>();
    List<String> list = new ArrayList<>();
    ChildrenByAddressDTO childrenByAddressDTO = new ChildrenByAddressDTO();
    FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
    List<FamilyMemberDTO> familyMemberDTOS = new ArrayList<>();



    @BeforeEach
    public void setUp() throws ParseException {
       /* PersonByStationDTO personByStationDTO = new PersonByStationDTO();
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();
        HouseholdDTO householdDTO = new HouseholdDTO();
        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();*/

        childrenByAddressDTO.setAge(10);
        childrenByAddressDTO.setFirstName("John");
        childrenByAddressDTO.setLastName("Doe");

        familyMemberDTO.setFirstName("Jack");
        familyMemberDTO.setFirstName("Doe");
        familyMemberDTOS.add(familyMemberDTO);
        childrenByAddressDTO.setFamillyMembers(familyMemberDTOS);
        childrenByAddressDTOS.add(childrenByAddressDTO);

    }


    @Test
    @DisplayName("test get children by address Succes")
    void testGetChildrenByAddress() throws Exception {

        when(commonService.getChildByAddress("toto")).thenReturn(childrenByAddressDTOS);

        mockMvc.perform(get("/api/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "toto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(childrenByAddressDTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(childrenByAddressDTO.getLastName())))
                .andExpect(jsonPath("$[0].age", is(childrenByAddressDTO.getAge())))
                .andExpect(jsonPath("$[0].famillyMembers", hasSize(1)));

        verify(commonService,times(1)).getChildByAddress("toto");
    }

    @Test
    @DisplayName("test get citizens email by city Succes")
    void testGetAllCitizenEmail() throws Exception {

        List<String> emailList = new ArrayList<>();
        emailList.add("toto@nomail.com");
        emailList.add("Jack@Sparrow.mail.fr");

        when(commonService.getAllCitizenEmail("Bordeaux")).thenReturn(emailList);

        mockMvc.perform(get("/api/communityEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("city", "Bordeaux"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("toto@nomail.com")))
                .andExpect(jsonPath("$[1]", is("Jack@Sparrow.mail.fr")));

        verify(commonService,times(1)).getAllCitizenEmail("Bordeaux");
    }

   /* @Test
    @DisplayName("test get persons from a station number Succes")
    void testGetPersonsListFromStationNumber() throws Exception {

        PersonByStationDTO personByStationDTO = new PersonByStationDTO();
        List<PersonForStationDTO> personForStationDTOList = new ArrayList<>();

        when(commonService.personsByStationNumber("1")).thenReturn(personByStationDTO);

        mockMvc.perform(get("/api/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("city", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("toto@nomail.com")))
                .andExpect(jsonPath("$[1]", is("Jack@Sparrow.mail.fr")));

        verify(commonService,times(1)).getAllCitizenEmail("Bordeaux");
    }*/
}
