package com.SafetyNet.Alert.Dto;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
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
