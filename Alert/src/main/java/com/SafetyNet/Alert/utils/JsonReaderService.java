package com.SafetyNet.Alert.utils;

import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Service.FirestationService;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import com.SafetyNet.Alert.Service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonReaderService {

    @Autowired
    PersonService personService;

    @Autowired
    FirestationService firestationService;

    @Autowired
    MedicalrecordsService medicalrecordsService;

    public void process() throws IOException, ParseException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("data.json"));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

        List<Persons> lstPerson = readListPersonFromJsonObject(jsonObject);
        List<Firestations> firestationsList = readListFirestationFromJsonObject(jsonObject);
        List<Medicalrecords> medicalrecordsList = readListMedicalRecordsFromJsonObject(jsonObject);

        personService.saveAll(lstPerson);
        firestationService.saveAll(firestationsList);
        medicalrecordsService.saveAll(medicalrecordsList);
    }

    private List<Persons> readListPersonFromJsonObject(JSONObject jsonObject) {
        JSONArray personsInJson = (JSONArray) jsonObject.get("persons");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Persons> personList = new ArrayList<>();
        personsInJson.forEach(itemArray ->
        {
            try {
                personList.add(objectMapper.readValue(itemArray.toString(), Persons.class));

            } catch (JsonProcessingException exception) {

/*
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
*/
            }
        });

        return personList;

    }

    private List<Firestations> readListFirestationFromJsonObject(JSONObject jsonObject) {
        JSONArray firestationsInJson = (JSONArray) jsonObject.get("firestations");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Firestations> firestationsList = new ArrayList<>();
        firestationsInJson.forEach(itemArray ->
        {
            try {
                firestationsList.add(objectMapper.readValue(itemArray.toString(), Firestations.class));

            } catch (JsonProcessingException exception) {

/*
                logger.error("Error while parsing input json file - firestations : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
*/
            }
        });

        return firestationsList;

    }

    private List<Medicalrecords> readListMedicalRecordsFromJsonObject(JSONObject jsonObject) {

        JSONArray medicalRecordsInJson = (JSONArray) jsonObject.get("medicalrecords");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Medicalrecords> medicalrecordsList = new ArrayList<>();
        medicalRecordsInJson.forEach(itemArray ->
        {
            try {
                medicalrecordsList.add(objectMapper.readValue(itemArray.toString(), Medicalrecords.class));

            } catch (JsonProcessingException exception) {

                System.out.println(exception.getMessage());
/*
                logger.error("Error while parsing input json file - medicalRecords : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
*/
            }
        });

        return medicalrecordsList;

    }

}
