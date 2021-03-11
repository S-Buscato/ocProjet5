package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonForStationDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
