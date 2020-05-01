package com.restfulspring.apiexample.service;

import com.restfulspring.apiexample.entity.Person;
import com.restfulspring.apiexample.exception.NotFoundException;
import com.restfulspring.apiexample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    public Person getPerson(int id){
        Optional<Person> person = personRepository.findById(id);
        if(!person.isPresent()){
            throw new NotFoundException("Person not found!");
        }
        return person.get();
    }

    public Person addPerson(Person person){
        personRepository.save(person);
        return person;
    }

    public Person updatePerson(int id,Person person){
        person.setId(id);
        personRepository.save(person);
        return person;
    }

    public void deletePerson(int id){
        personRepository.deleteById(id);
    }
}
