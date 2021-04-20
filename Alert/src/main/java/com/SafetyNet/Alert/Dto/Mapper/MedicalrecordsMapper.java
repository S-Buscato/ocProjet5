package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.MedicalRecordsDTO;
import com.SafetyNet.Alert.Model.Medicalrecords;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel="spring")
@Service
public interface MedicalrecordsMapper {

    MedicalrecordsMapper INSTANCE = Mappers.getMapper(MedicalrecordsMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MedicalRecordsDTO medicalrecordsToMedicalrecordsDTO(Medicalrecords entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicalrecords MedicalrecordsDTOtomedicalrecords(MedicalRecordsDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MedicalRecordsDTO> medicalrecordsToMedicalrecordsDTO(List<Medicalrecords> medicalrecords);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MedicalRecordsDTO convertMedicalRecordsUpdateDtoToMedicalRecordsDTO(MedicalRecordsDTO medicalRecordsDTO );
}
