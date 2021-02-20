package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.FirestationDTO;
import com.SafetyNet.Alert.Model.Firestations;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface FirestationMapper {

    FirestationMapper INSTANCE = Mappers.getMapper(FirestationMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FirestationDTO firestationToFirestationDTO(Firestations entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Firestations firestationDTOtoFirestation(FirestationDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<FirestationDTO> firestationToFirestationsDTO(List<Firestations> firestationList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Firestations firestationUpdateDtoToFirestationUpdate(FirestationDTO firestationDTO, @MappingTarget Firestations entity);
}
