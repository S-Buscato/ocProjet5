package com.SafetyNet.Alert;

import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Service.PersonService;
import com.SafetyNet.Alert.utils.JsonReaderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
public class AlertApplication implements CommandLineRunner {

    public static void main(String[] args)  {
        SpringApplication.run(AlertApplication.class, args);
    }


    @Autowired
    JsonReaderService jsonReaderService;

    @Override
    public void run(String... args) throws Exception {
        jsonReaderService.process();
    }
}
