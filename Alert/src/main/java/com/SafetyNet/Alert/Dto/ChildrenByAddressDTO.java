package com.SafetyNet.Alert.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenByAddressDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<FamilyMemberDTO> famillyMembers;
}
