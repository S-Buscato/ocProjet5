package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonByStationDTO {

    private Collection<PersonForStationDTO> personsForStationNumber;
    private int adultNumber = 0;
    private int childrenNumber = 0;


    public void addOneAdult() {
        adultNumber += 1;
    }

    public void addOneChildren() {
        childrenNumber += 1;
    }

}
