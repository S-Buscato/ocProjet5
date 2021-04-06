package com.SafetyNet.Alert.Controller;

import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Service.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CommonController {

    @Autowired
    CommonService commonService;

    static Logger logger = Logger.getLogger(CommonController.class);

    @GetMapping(value = "childAlert")
    public ResponseEntity<List<ChildrenByAddressDTO>> getChildrenByAddress(@RequestParam String address) {
        try {
            logger.info("Api/childAlert ok");
            return ResponseEntity.ok(commonService.getChildByAddress(address));

        } catch (Exception e) {
            logger.error("Api/childAlert error  : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "communityEmail")
    public ResponseEntity<List<String>> getAllCitizenEmail(@RequestParam String city) {
        try {
            logger.info("Api/communityEmail ok");
            return ResponseEntity.ok(commonService.getAllCitizenEmail(city));

        } catch (Exception e) {
            logger.error("Api/communityEmail error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "firestation")
    public ResponseEntity<PersonByStationDTO> getPersonsListFromStationNumber(@RequestParam String stationNumber) {
        try {
            logger.info("Api/firestation ok");
            return ResponseEntity.ok(commonService.personsByStationNumber(stationNumber));

        } catch (Exception e) {
            logger.error("Api/firestation error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "phoneAlert")
    public ResponseEntity<Set<String>> getPhoneNumberFromStationNumber(@RequestParam String firestation) {
        try {
            logger.info("Api/phoneAlert ok");
            return ResponseEntity.ok(commonService.getPhoneNumberByStationNumber(firestation));

        } catch (Exception e) {
            logger.error("Api/phoneAlert error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "personInfo")
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfoByFirstNameAndLastName(@RequestParam String firstName, String lastName) {
        try {
            logger.info("Api/personInfo ok");
            return ResponseEntity.ok(commonService.getPersonInfoByFirstNameAndLastName(firstName, lastName));

        } catch (Exception e) {
            logger.error("Api/personInfo error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "flood/stations")
    public ResponseEntity<List<HouseholdDTO>> getHouseholdByStation(@RequestParam List<String> stations) {
        try {
            logger.info("Api/flood/station ok");
            return ResponseEntity.ok(commonService.householdByStationNumber(stations));

        } catch (Exception e) {
            logger.error("Api/flood/station error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "fire")
    public ResponseEntity<List<PersonsByAddressDTO>> getPersonsAndFirestationNumberByAddress(@RequestParam String address) {
        try {
            logger.info("Api/fire ok");
            return ResponseEntity.ok(commonService.getPersonsAndFireStationNumber(address));

        } catch (Exception e) {
            logger.error("Api/fire error : " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
