package com.SafetyNet.Alert;

import com.SafetyNet.Alert.Util.JsonReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AlertApplication.class, args);
    }


    @Autowired
    JsonReaderService jsonReaderService;

    @Override
    public void run(String... args) throws Exception {
        jsonReaderService.process();
    }
}
