package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Constants.PersonData;
import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Dto.Mapper.*;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Repository.MedicalserviceRepository;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CommonService implements ICommonService {
    @Autowired
    MedicalserviceRepository medicalserviceRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    PersonRepository personRepository;

    public int calculAge(Date date){
        if(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).equals(date)){
            return 0;
        }else{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return Period.between(LocalDate.parse(simpleDateFormat.format(date), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();
        }
    }

    public Date getBirthdate(Persons person) throws ParseException {
        String birthdate;
        if(medicalserviceRepository.getBirhdate(person.getFirstName(), person.getLastName()) != null){
            birthdate = medicalserviceRepository.getBirhdate(person.getFirstName(), person.getLastName());
        }
        else{
           birthdate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
    }

    @Override
    public List<Persons> getPersonsByStationNumber(String stationNumber){
        return firestationRepository.getPersonsByStationNumber(stationNumber);
    }

    @Override
    public Firestations findByAddress(String address) {
        return firestationRepository.findFirestationsByAddress(address);
    }

    public Medicalrecords findMedicalRecordsOfAPerson(Persons p){
        Medicalrecords medicalrecords = new Medicalrecords();
        if(medicalserviceRepository.findByfirstNameAndLastName(p.getFirstName(), p.getLastName()) != null){
            medicalrecords = medicalserviceRepository.findByfirstNameAndLastName(p.getFirstName(), p.getLastName());
        }else{
            medicalrecords.setFirstName(p.getFirstName());
            medicalrecords.setLastName(p.getLastName());
        }
        return medicalrecords;
    }


    public Set<String> getPhoneNumberByStationNumber(String stationNumber) {
        List<Persons> personsList = firestationRepository.getPersonsByStationNumber(stationNumber);
        Set<String> phoneNumberList = new HashSet<String>();
        for (Persons p : personsList) {
            String phoneNumber = p.getPhone();
            phoneNumberList.add(phoneNumber);
        }
        return phoneNumberList;
    }

    public List<PersonsByAddressDTO> getPersonsAndFireStationNumber(String address) throws ParseException {
        List<Persons> personsList = personRepository.findByAddress(address);
        List<PersonsByAddressDTO> personsByAddressDTOList = new ArrayList<>();

        for (Persons person : personsList) {
            Firestations firestations = firestationRepository.findFirestationsByAddress(address);

            Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(person);

            PersonsByAddressDTO personsByAddressDTO = PersonByAddressMapper.
                    convertPersonToPersonForStationDTO(person, medicalrecords);

            personsByAddressDTO.setStationNumber(firestations.getStation());

            personsByAddressDTO.setAge(this.calculAge(this.getBirthdate(person)));
            personsByAddressDTOList.add(personsByAddressDTO);
        }
        return personsByAddressDTOList;
    }

    public List<HouseholdDTO> householdByStationNumber(List<String> stations) throws ParseException {
        Set<HouseholdDTO> householdDTOList = new HashSet<>();
        for (String i : stations){
            List<Persons> personsList = firestationRepository.getPersonsByStationNumber(i);
            for (Persons persons:personsList) {
                HouseholdDTO householdDTO = new HouseholdDTO();
                householdDTO.setAddress(persons.getAddress());
                householdDTO.setStationNumber(i);
                householdDTOList.add(householdDTO);
            }
        }
        for(HouseholdDTO h : householdDTOList){
            List<Persons> personsList = personRepository.findByAddress(h.getAddress());
            Set<PersonsByAddressDTO> personsByAddressDTOList = new HashSet<>();
            for (Persons p : personsList){

                    Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(p);

                    PersonsByAddressDTO personsByAddressDTO = PersonByAddressMapper.
                            convertPersonToPersonForStationDTO(p, medicalrecords);

                    personsByAddressDTO.setAge(this.calculAge(this.getBirthdate(p)));
                    personsByAddressDTO.setStationNumber(h.getStationNumber());

                    personsByAddressDTOList.add(personsByAddressDTO);
                    List<PersonsByAddressDTO> personsByAddressDTOS = new ArrayList<>(personsByAddressDTOList);
                    h.setHousehold(personsByAddressDTOS);
            }
        }
        List<HouseholdDTO> householdDTOS = new ArrayList<>(householdDTOList) ;
        return  householdDTOS;
    }

    public PersonByStationDTO personsByStationNumber(String stationNumber) throws ParseException {
        PersonByStationDTO personByStationDTO = new PersonByStationDTO();
        List<PersonForStationDTO> personForStationDTOList = new ArrayList<>();
        List<Persons> personsList = this.getPersonsByStationNumber(stationNumber);

        for (Persons person : personsList) {
            PersonForStationDTO personForStationDTO = PersonForSationMapper.
                    convertPersonToPersonForStationDTO(person);

            int age = this.calculAge(this.getBirthdate(person));
            if(age != 0){
                if (age < PersonData.AGE_MAJORITE) {
                    personByStationDTO.addOneChildren();
                } else {
                    personByStationDTO.addOneAdult();
                }
            }
            personForStationDTOList.add(personForStationDTO);
        }
        personByStationDTO.setPersonsForStationNumber(personForStationDTOList);
        return personByStationDTO;
    }


    public List<PersonInfoDTO> getPersonInfoByFirstNameAndLastName(String firstName, String lastName) throws ParseException {
        PersonDTO personDTO = PersonMapper.convertPersonToPersonDto(personRepository.findByfirstNameAndLastName(firstName, lastName));
        Persons persons= personRepository.findByfirstNameAndLastName(personDTO.getFirstName(), personDTO.getLastName());

        List<Persons> personsList = personRepository.findByAddress(persons.getAddress());
        List<PersonInfoDTO>  personInfoDTOS = new ArrayList<>();

        for (Persons p : personsList){
            //TODO : attention si on vire le dossier medical
            Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(p);
            PersonInfoDTO personInfoDTO = PersonInfoMapper.convertPersonToPersonInfoDto(p, medicalrecords);
            personInfoDTO.setAge(this.calculAge(this.getBirthdate(p)));
            personInfoDTOS.add(personInfoDTO);
        }
        return personInfoDTOS;
    }

    public List<ChildrenByAddressDTO> getChildByAddress(String address) throws ParseException {
        List<Persons> personsList = personRepository.findByAddress(address);
        List<ChildrenByAddressDTO> childrenByAddressDTOS = new ArrayList<>();

        for (Persons person : personsList) {
            // TODO : ATTENTION si on vire du medical records il n'y a plus d'age
            int age = this.calculAge(this.getBirthdate(person));
            if( age < PersonData.AGE_MAJORITE){

                ChildrenByAddressDTO childrenByAddressDTO = ChildrenByAddressMapper
                        .convertPersonToChildrenByAddressDTO(person);
                childrenByAddressDTO.setAge(age);

                List<Persons> persons = personRepository.findByAddress(address);
                List<FamilyMemberDTO> familyMemberDTOS = new ArrayList<>();
                for (Persons p : persons) {
                    if(!p.getFirstName().equals(childrenByAddressDTO.getFirstName())){
                        FamilyMemberDTO familyMemberDTO = FamilyMemberMapper
                                .convertPersonToFamillyMemberDTO(p);
                        familyMemberDTOS.add(familyMemberDTO);
                    }
                }
                childrenByAddressDTO.setFamillyMembers(familyMemberDTOS);
                childrenByAddressDTOS.add(childrenByAddressDTO);
            }
        }
        return childrenByAddressDTOS;
    }


    public List<String> getAllCitizenEmail(String city) {
        return new ArrayList<>(personRepository.citizenEmail(city));
    }
}
