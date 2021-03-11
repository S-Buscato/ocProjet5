package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.ChildrenByAddressDTO;
import com.SafetyNet.Alert.Model.Persons;

public class ChildrenByAddressMapper {

    public static ChildrenByAddressDTO convertPersonToChildrenByAddressDTO(Persons persons){
        ChildrenByAddressDTO childrenByAddressDTO = new ChildrenByAddressDTO();
        childrenByAddressDTO.setFirstName(persons.getFirstName() == null? "" : persons.getFirstName());
        childrenByAddressDTO.setLastName(persons.getLastName() == null? "" : persons.getLastName());
        return childrenByAddressDTO;
    }
}
