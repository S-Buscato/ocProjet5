package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Dto.MedicalRecordsUpdateDTO;
import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface MedicalrecordsMapper {

    MedicalrecordsMapper INSTANCE = Mappers.getMapper(MedicalrecordsMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MedicalRecordsDTO medicalrecordsToMedicalrecordsDTO(Medicalrecords entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicalrecords MedicalrecordsDTOtomedicalrecords(MedicalRecordsDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MedicalRecordsDTO> medicalrecordsToMedicalrecordsDTO(List<Medicalrecords> medicalrecords);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicalrecords medicalRecordsUpdateDtoToMedicalRecordsUpdate(MedicalRecordsUpdateDTO medicalRecordsUpdateDTO, @MappingTarget Medicalrecords entity);
}
