package com.SafetyNet.Alert.Util;

import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Service.FirestationService;
import com.SafetyNet.Alert.Service.MedicalrecordsService;
import com.SafetyNet.Alert.Service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonReaderService {

    @Autowired
    FirestationService firestationService;

    @Autowired
    MedicalrecordsService medicalrecordsService;

    @Autowired
    PersonService personService;

    static Logger logger = Logger.getLogger(JsonReaderService.class);

    public void process() throws IOException, ParseException {

        Path resourceDirectoryTest = Paths.get("src","resources");
        Path resourceDirectoryRun = Paths.get("Alert","src","resources");
        String absolutePathTest = resourceDirectoryTest.toFile().getAbsolutePath()+ "\\datatest.json"; ;
        String absolutePathRun = resourceDirectoryRun.toFile().getAbsolutePath()+ "\\data.json";

        if(Files.isDirectory(resourceDirectoryRun)){
            logger.info("Running AlertApplication => reading data.json...");
            String rootPath = (Files.isDirectory(resourceDirectoryTest))? absolutePathTest : absolutePathRun;
            System.out.println(rootPath);
            this.getData(rootPath);
        }else{
            logger.info("Running Testing AlertApplication ...");
        }
    }

    private void getData(String rootPath) throws IOException, ParseException{
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(rootPath));

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(inputStreamReader);

        List<Persons> lstPerson = new ArrayList<>();
        List<Firestations>  firestationsList = new ArrayList<>();
        List<Medicalrecords> medicalrecordsList = new ArrayList<>();

        try{
            lstPerson = readListPersonFromJsonObject(jsonObject);
            logger.info("read persons ok");
        }catch (Exception e){
            logger.error("Error reading persons : " + e.getMessage());
        }

        try{
            firestationsList = readListFirestationFromJsonObject(jsonObject);
            logger.info("read firestation ok");
        }catch (Exception e){
            logger.error("Error reading firestation : "+ e.getMessage());
        }

        try{
            medicalrecordsList = readListMedicalRecordsFromJsonObject(jsonObject);
            logger.info("read medicalrecords ok");
        }catch (Exception e){
            logger.error("Error reading medicalrecords : " + e.getMessage());
        }

        try{
            personService.saveAll(lstPerson);
            logger.info("add persons in BDD ok");
        }catch (Exception e){
            logger.error("Error saving persons : "+ e.getMessage() );
        }

        try{
            firestationService.saveAll(firestationsList);
            logger.info("add firestation in BDD ok");
        }catch (Exception e){
            logger.error("Error saving firestation : "+ e.getMessage());
        }

        try{
            medicalrecordsService.saveAll(medicalrecordsList);
            logger.info("add medicalrecords in BDD ok");
        }catch (Exception e){
            logger.error("Error saving medicalrecords : " + e.getMessage());
        }
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
                logger.error("Error while parsing input json file - persons : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
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
                logger.error("Error while parsing input json file - firestations : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
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
                logger.error("Error while parsing input json file - medicalRecords : " + exception.getMessage() + " Stack Strace : " + exception.getStackTrace());
            }
        });

        return medicalrecordsList;

    }

}
