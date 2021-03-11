package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.FamilyMemberDTO;
import com.SafetyNet.Alert.Model.Persons;

public class FamilyMemberMapper {

    public static FamilyMemberDTO convertPersonToFamillyMemberDTO(Persons persons){
        FamilyMemberDTO FamilyMemberDTO = new FamilyMemberDTO();
        FamilyMemberDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        FamilyMemberDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        return FamilyMemberDTO;
    }
}

