package com.SafetyNet.Alert.Dto.Mapper;

import com.SafetyNet.Alert.Dto.PersonDTO;
import com.SafetyNet.Alert.Dto.PersonUpdateDTO;
import com.SafetyNet.Alert.Model.Persons;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface PersonMapper {


    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonDTO personToPersonDTO(Persons entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Persons personDTOtoPerson(PersonDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<PersonDTO> personToPersonsDTO(List<Persons> persons);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Persons personUpdateDtoToPersonUpdate(PersonUpdateDTO person,  @MappingTarget Persons    entity);
}
