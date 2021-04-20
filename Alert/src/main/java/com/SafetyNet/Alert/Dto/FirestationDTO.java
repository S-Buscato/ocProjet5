package com.SafetyNet.Alert.Dto;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class FirestationDTO {
    @Id
    private long id;
    private String address;
    private String station;
}
