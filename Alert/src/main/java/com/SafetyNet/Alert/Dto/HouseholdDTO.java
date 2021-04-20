package com.SafetyNet.Alert.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HouseholdDTO {
    private String stationNumber;
    private String address;
    private List<PersonsByAddressDTO> household;
}
