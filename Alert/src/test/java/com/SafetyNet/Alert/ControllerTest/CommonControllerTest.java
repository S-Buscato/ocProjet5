package com.SafetyNet.Alert.ControllerTest;


import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Service.CommonService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    @DisplayName("test get children by address Succes")
    void testGetChildrenByAddress() throws Exception {
        List<ChildrenByAddressDTO> childrenByAddressDTOS = new ArrayList<>();
        ChildrenByAddressDTO childrenByAddressDTO = new ChildrenByAddressDTO();
        FamilyMemberDTO familyMemberDTO = new FamilyMemberDTO();
        List<FamilyMemberDTO> familyMemberDTOS = new ArrayList<>();

        childrenByAddressDTO.setAge(10);
        childrenByAddressDTO.setFirstName("John");
        childrenByAddressDTO.setLastName("Doe");

        familyMemberDTO.setFirstName("Jack");
        familyMemberDTO.setFirstName("Doe");
        familyMemberDTOS.add(familyMemberDTO);
        childrenByAddressDTO.setFamillyMembers(familyMemberDTOS);
        childrenByAddressDTOS.add(childrenByAddressDTO);

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
    @DisplayName("test get children by address return error Succes")
    void testGetChildrenByAddressReturnError() throws Exception {
        Exception e = new IllegalArgumentException();
        when(commonService.getChildByAddress("toto")).thenThrow(e);

        mockMvc.perform(get("/api/childAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "toto"))
                .andExpect(status().is5xxServerError());

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

    @Test
    @DisplayName("test get citizens email by city return Error Succes")
    void testGetAllCitizenEmailReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        when(commonService.getAllCitizenEmail("Bordeaux")).thenThrow(e);

        mockMvc.perform(get("/api/communityEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("city", "Bordeaux"))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).getAllCitizenEmail("Bordeaux");
    }

    @Test
    @DisplayName("test get persons covered by station number Succes")
    void testGetPersonsListFromStationNumber() throws Exception {

        PersonByStationDTO personByStationDTO = new PersonByStationDTO();
        List<PersonForStationDTO> personForStationDTOList = new ArrayList<>();

        PersonForStationDTO personForStationDTO = new PersonForStationDTO();
        personForStationDTO.setLastName("John");
        personForStationDTO.setFirstName("Doe");
        personForStationDTO.setPhone("01010101");
        personForStationDTO.setAddress("rue des invisibles");

        personForStationDTOList.add(personForStationDTO);

        personByStationDTO.setAdultNumber(1);
        personByStationDTO.setChildrenNumber(1);
        personByStationDTO.setPersonsForStationNumber(personForStationDTOList);

        when(commonService.personsByStationNumber("1")).thenReturn(personByStationDTO);

        mockMvc.perform(get("/api/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personsForStationNumber", hasSize(1)))
                .andExpect(jsonPath("$.adultNumber", is(1)))
                .andExpect(jsonPath("$.childrenNumber", is(1)));

        verify(commonService,times(1)).personsByStationNumber("1");
    }

    @Test
    @DisplayName("test get persons covered by station number return Error Succes")
    void testGetPersonsListFromStationNumberReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        when(commonService.personsByStationNumber("1")).thenThrow(e);

        mockMvc.perform(get("/api/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stationNumber", "1"))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).personsByStationNumber("1");
    }

    @Test
    @DisplayName("test all phone numbers covered by a firestation Success")
    void testGetPhoneNumberFromStationNumber() throws Exception {

        Set<String> phoneNumberList = new HashSet<String>();
        phoneNumberList.add("01010101");
        phoneNumberList.add("02020202");

        when(commonService.getPhoneNumberByStationNumber("1")).thenReturn(phoneNumberList);

        mockMvc.perform(get("/api/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("02020202")))
                .andExpect(jsonPath("$[1]", is("01010101")));

        verify(commonService,times(1)).getPhoneNumberByStationNumber("1");
    }

    @Test
    @DisplayName("test all phone numbers covered by a firestation Return Error Success")
    void testGetPhoneNumberFromStationNumberReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        when(commonService.getPhoneNumberByStationNumber("1")).thenThrow(e);

        mockMvc.perform(get("/api/phoneAlert")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firestation", "1"))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).getPhoneNumberByStationNumber("1");
    }


    @Test
    @DisplayName("test get person info Success")
    void getPersonInfoByFirstNameAndLastName() throws Exception {
        List<PersonInfoDTO> personInfoDTOS = new ArrayList<>();
        PersonInfoDTO personInfoDTO = new PersonInfoDTO();

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        personInfoDTO.setAge(15);
        personInfoDTO.setLastName("Doe");
        personInfoDTO.setFirstName("John");
        personInfoDTO.setMail("john.doe@nomail.com");
        personInfoDTO.setAdresse("rue des invisibles");
        personInfoDTO.setMedication(medications);
        personInfoDTO.setAllergies(allergies);

        personInfoDTOS.add(personInfoDTO);

        when(commonService.getPersonInfoByFirstNameAndLastName("John", "Doe")).thenReturn(personInfoDTOS);

        mockMvc.perform(get("/api/personInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].medication", hasSize(1)))
                .andExpect(jsonPath("$[0].allergies", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")));

        verify(commonService,times(1)).getPersonInfoByFirstNameAndLastName("John","Doe");
    }

    @Test
    @DisplayName("test get person info return error Success")
    void getPersonInfoByFirstNameAndLastNameReturnError() throws Exception {
        Exception e = new IllegalArgumentException();

        when(commonService.getPersonInfoByFirstNameAndLastName("John", "Doe")).thenThrow(e);

        mockMvc.perform(get("/api/personInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).getPersonInfoByFirstNameAndLastName("John","Doe");
    }

    @Test
    @DisplayName("test get houseHold covered by stations numbers Success")
    void getHouseholdByStation() throws Exception {

        List<HouseholdDTO> householdDTOList = new ArrayList<>();
        HouseholdDTO householdDTO = new HouseholdDTO();
        householdDTO.setStationNumber("1");
        householdDTO.setAddress("rue des invisibles");

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setStationNumber("1");
        personsByAddressDTO.setAge(45);
        personsByAddressDTO.setFirstName("John");
        personsByAddressDTO.setLastName("Doe");
        personsByAddressDTO.setPhone("01010101");
        personsByAddressDTO.setAllergies(allergies);
        personsByAddressDTO.setMedication(medications);

        List<PersonsByAddressDTO> personsByAddressDTOS = new ArrayList<>();
        personsByAddressDTOS.add(personsByAddressDTO);

        householdDTO.setHousehold(personsByAddressDTOS);
        householdDTOList.add(householdDTO);
        List<String> stations = new ArrayList<>();
        stations.add("1");
        stations.add("2");
        when(commonService.householdByStationNumber(stations)).thenReturn(householdDTOList);

        String station = "1,2";
        mockMvc.perform(get("/api/flood/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stations", station))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].address", is("rue des invisibles")));
                //.andExpect(jsonPath("$[0]", is("01010101")));

        verify(commonService,times(1)).householdByStationNumber(stations);
    }

    @Test
    @DisplayName("test get houseHold covered by stations numbers Return Error Success")
    void getHouseholdByStationReturnError() throws Exception {

        Exception e = new IllegalArgumentException();

        when(commonService.householdByStationNumber(anyList())).thenThrow(e);

        String station = "1,2";
        mockMvc.perform(get("/api/flood/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stations", station))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).householdByStationNumber(anyList());
    }

    @Test
    @DisplayName("test get persons covered by a firestation address Success")
    void getPersonsAndFirestationNumberByAddress() throws Exception {
        List<PersonsByAddressDTO> personsByAddressDTOList = new ArrayList<>();

        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();

        medications.add("paracetamol:500");
        allergies.add("chat");
        allergies.add("chien");

        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        personsByAddressDTO.setStationNumber("1");
        personsByAddressDTO.setAge(45);
        personsByAddressDTO.setFirstName("John");
        personsByAddressDTO.setLastName("Doe");
        personsByAddressDTO.setPhone("01010101");
        personsByAddressDTO.setAllergies(allergies);
        personsByAddressDTO.setMedication(medications);

        personsByAddressDTOList.add(personsByAddressDTO);

        when(commonService.getPersonsAndFireStationNumber("rue des invisibles")).thenReturn(personsByAddressDTOList);

        mockMvc.perform(get("/api/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "rue des invisibles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));

        verify(commonService,times(1)).getPersonsAndFireStationNumber("rue des invisibles");
    }

    @Test
    @DisplayName("test get persons covered by a firestation address Return Error Success")
    void getPersonsAndFirestationNumberByAddressReturnError() throws Exception {
        Exception e = new IllegalArgumentException();


        when(commonService.getPersonsAndFireStationNumber("rue des invisibles")).thenThrow(e);

        mockMvc.perform(get("/api/fire")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "rue des invisibles"))
                .andExpect(status().is5xxServerError());

        verify(commonService,times(1)).getPersonsAndFireStationNumber("rue des invisibles");
    }

}
