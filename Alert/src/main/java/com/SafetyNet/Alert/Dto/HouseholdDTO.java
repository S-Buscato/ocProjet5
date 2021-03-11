package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdDTO {
    private String stationNumber;
    private String address;
    private List<PersonsByAddressDTO> household;
}
