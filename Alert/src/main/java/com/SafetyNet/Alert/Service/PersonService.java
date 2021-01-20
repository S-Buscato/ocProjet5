package com.SafetyNet.Alert.Service;

import com.SafetyNet.Alert.Model.Person;
import com.SafetyNet.Alert.Repository.PersonRepository;
import com.SafetyNet.Alert.Service.Iservice.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        try {
            List<Person> ret = StreamSupport.stream(personRepository.findAll().spliterator(),
                    false).collect(Collectors.toList());
            return ret;

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Optional<Person> findById(Long id) {
        try {
            return personRepository.findById(id);
        } catch (Exception e) {

        }

        return null;
    }


    public Long deleteById(Long id) {
        try {
            personRepository.deleteById(id);
            return id;
        } catch (Exception e) {

        }
        return null;
    }

    public int save(Person person) {
        try {
            personRepository.save(person);
        } catch (Exception e) {

        }
        return 0;
    }

    //TODO : faire controle si ID existe
    public int update(Person person, Long id) {
        String sql = "" +
                "UPDATE TBL_PERSONS " +
                "SET firstname = ?, lastname = ?, address = ?, " "city = ?, zip = ?, phone = ?, email = ?" +
                "WHERE ID =?";

        Object[] params = new Object[]{
                person.getFirstname(), person.getLastname(), person.getAddress(),
                person.getCity(), person.getZip(), person.getPhone(), person.getEmail(), id
        };

        int[] types = new int[]{
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT
        };

        return jtm.update(sql, params, types);
    }
}
