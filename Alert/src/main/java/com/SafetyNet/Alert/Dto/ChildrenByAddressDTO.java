package com.SafetyNet.Alert.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChildrenByAddressDTO {

    private String firstName;
    private String lastName;
    private int age;
    private List<FamilyMemberDTO> famillyMembers;
}
