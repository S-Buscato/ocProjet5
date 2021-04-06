package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Constants.PersonData;
import com.SafetyNet.Alert.Dto.*;
import com.SafetyNet.Alert.Dto.Mapper.*;
import com.SafetyNet.Alert.Model.Firestations;
import com.SafetyNet.Alert.Model.Medicalrecords;
import com.SafetyNet.Alert.Model.Persons;
import com.SafetyNet.Alert.Repository.FirestationRepository;
import com.SafetyNet.Alert.Repository.MedicalRecordsRepository;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.ICommonService;
import org.apache.log4j.Logger;
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
    MedicalRecordsRepository medicalRecordsRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    PersonRepository personRepository;

    @Autowired
    DtoMapper dtoMapper;


    static Logger logger = Logger.getLogger(CommonService.class);

    public int calculAge(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        logger.debug("calcul age");
        return Period.between(LocalDate.parse(simpleDateFormat.format(date), DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalDate.now()).getYears();
    }

    public Date getBirthdate(Persons person) throws ParseException {
        String birthdate;
        if(medicalRecordsRepository.getBirhdate(person.getFirstName(), person.getLastName()) != null){
            logger.debug(person.getFirstName() + " " + person.getLastName() + " : date de naissance connue");
            birthdate = medicalRecordsRepository.getBirhdate(person.getFirstName(), person.getLastName());
        }
        else{
            logger.debug(person.getFirstName() + " " + person.getLastName() +  " : pas de date de naissance connue");
            birthdate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
    }

    @Override
    public List<Persons> getPersonsByStationNumber(String stationNumber){
        logger.debug("getPersonsByStationNumber return PersonsList");
        return firestationRepository.getPersonsByStationNumber(stationNumber);
    }

    @Override
    public Firestations findByAddress(String address) {
        logger.debug("findByAddress return Firestations");
        return firestationRepository.findFirestationsByAddress(address);
    }

    public Medicalrecords findMedicalRecordsOfAPerson(Persons p){
        Medicalrecords medicalrecords = new Medicalrecords();
        if(medicalRecordsRepository.findByfirstNameAndLastName(p.getFirstName(), p.getLastName()) != null){
            logger.debug("findMedicalRecordsOfAPerson a Person medicalRecords was found");
            medicalrecords = medicalRecordsRepository.findByfirstNameAndLastName(p.getFirstName(), p.getLastName());
        }else{
            logger.debug("findMedicalRecordsOfAPerson a Person medicalRecords was not found");
            medicalrecords.setFirstName(p.getFirstName());
            medicalrecords.setLastName(p.getLastName());
        }
        return medicalrecords;
    }


    public Set<String> getPhoneNumberByStationNumber(String stationNumber) {
        logger.debug("getPhoneNumberByStationNumber return a set of String phones numbers");
        List<Persons> personsList = firestationRepository.getPersonsByStationNumber(stationNumber);
        Set<String> phoneNumberList = new HashSet<String>();
        for (Persons p : personsList) {
            String phoneNumber = p.getPhone();
            phoneNumberList.add(phoneNumber);
        }
        return phoneNumberList;
    }

    public PersonsByAddressDTO getPersonByAddressDtoConvert(Persons person, Medicalrecords medicalrecords){
        logger.debug("getPersonByAddressDtoConvert return a personByAddressDtoConverted");
        PersonsByAddressDTO personsByAddressDTO = new PersonsByAddressDTO();
        if(person != null & medicalrecords != null){
            return dtoMapper.convertPersonToPersonForStationDTO(person, medicalrecords);
        }
        return  personsByAddressDTO;
    }


    public List<PersonsByAddressDTO> getPersonsAndFireStationNumber(String address) throws ParseException {
        List<Persons> personsList = personRepository.findByAddress(address);
        List<PersonsByAddressDTO> personsByAddressDTOList = new ArrayList<>();
        logger.debug("getPersonsAndFireStationNumber : from an adress get persons and find firestation, medicalrecords and calcul age");
        for (Persons person : personsList) {
            Firestations firestations = firestationRepository.findFirestationsByAddress(address);

            Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(person);

            PersonsByAddressDTO personsByAddressDTO = this.getPersonByAddressDtoConvert(person, medicalrecords);

            personsByAddressDTO.setStationNumber(firestations.getStation());

            personsByAddressDTO.setAge(this.calculAge(this.getBirthdate(person)));

            personsByAddressDTOList.add(personsByAddressDTO);
        }
        logger.debug("getPersonsAndFireStationNumber return a PersonsByAddressDTO List");
        return personsByAddressDTOList;
    }

    public List<HouseholdDTO> householdByStationNumber(List<String> stations) throws ParseException {
        Set<HouseholdDTO> householdDTOList = new HashSet<>();
        logger.debug("householdByStationNumber get a person list from stationNumber");
        for (String i : stations){
            List<Persons> personsList = firestationRepository.getPersonsByStationNumber(i);
            logger.debug("householdByStationNumber : for each station address get person, set address and set station number of an housholdDTO");
            for (Persons persons:personsList) {
                HouseholdDTO householdDTO = new HouseholdDTO();
                householdDTO.setAddress(persons.getAddress());
                householdDTO.setStationNumber(i);
                householdDTOList.add(householdDTO);
            }
        }
        logger.debug("householdByStationNumber : for each householdDTO create a set of personsByAddressDTOList");
        for(HouseholdDTO h : householdDTOList){
            List<Persons> personsList = personRepository.findByAddress(h.getAddress());
            Set<PersonsByAddressDTO> personsByAddressDTOList = new HashSet<>();
            logger.debug("householdByStationNumber : for each personsByAddressDTOList find medicalRecords and calcul Age and add all in household list");
            for (Persons p : personsList){
                    Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(p);

                    PersonsByAddressDTO personsByAddressDTO = dtoMapper.
                            convertPersonToPersonForStationDTO(p, medicalrecords);

                    personsByAddressDTO.setAge(this.calculAge(this.getBirthdate(p)));
                    personsByAddressDTO.setStationNumber(h.getStationNumber());

                    personsByAddressDTOList.add(personsByAddressDTO);
                    List<PersonsByAddressDTO> personsByAddressDTOS = new ArrayList<>(personsByAddressDTOList);
                    h.setHousehold(personsByAddressDTOS);
            }
        }
        List<HouseholdDTO> householdDTOS = new ArrayList<>(householdDTOList) ;
        logger.debug("householdByStationNumber return a Household List");
        return  householdDTOS;
    }

    public PersonByStationDTO personsByStationNumber(String stationNumber) throws ParseException {
        PersonByStationDTO personByStationDTO = new PersonByStationDTO();
        List<PersonForStationDTO> personForStationDTOList = new ArrayList<>();
        List<Persons> personsList = this.getPersonsByStationNumber(stationNumber);

        for (Persons person : personsList) {
            PersonForStationDTO personForStationDTO = dtoMapper.
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
            Medicalrecords medicalrecords = this.findMedicalRecordsOfAPerson(p);
            PersonInfoDTO personInfoDTO = dtoMapper.
                    convertPersonToPersonInfoDto(p, medicalrecords);
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

                ChildrenByAddressDTO childrenByAddressDTO = dtoMapper.convertPersonToChildrenByAddressDTO(person);
                childrenByAddressDTO.setAge(age);

                List<Persons> persons = personRepository.findByAddress(address);
                List<FamilyMemberDTO> familyMemberDTOS = new ArrayList<>();
                for (Persons p : persons) {
                    if(!p.getFirstName().equals(childrenByAddressDTO.getFirstName())){
                        FamilyMemberDTO familyMemberDTO = dtoMapper.convertPersonToFamillyMemberDTO(p);
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
