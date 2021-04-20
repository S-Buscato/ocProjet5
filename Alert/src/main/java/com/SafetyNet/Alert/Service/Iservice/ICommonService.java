package com.SafetyNet.Alert.Service.Iservice;

import com.SafetyNet.Alert.Dto.HouseholdDTO;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Persons;
import org.springframework.data.repository.query.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ICommonService {
    int calculAge(Date date);
    Date getBirthdate(Persons persons) throws ParseException;
    List<Persons> getPersonsByStationNumber(@Param("stationNumber") String station);

    Firestations findByAddress(String address);
    List<HouseholdDTO> householdByStationNumber(List<String> stations) throws ParseException;

}
