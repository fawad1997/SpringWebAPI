package com.restfulspring.apiexample.controller;

import com.restfulspring.apiexample.entity.Person;
import com.restfulspring.apiexample.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping
    public List<Person> getPersons(){
        return  personService.getPersons();
    }

    @GetMapping(value = "/{id}")
    public Person getPerson(@PathVariable int id){
        return personService.getPerson(id);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person){
        return personService.addPerson(person);
    }
    @PutMapping(value = "/{id}")
    public Person updatePerson(@PathVariable int id,@RequestBody Person person){
        return personService.updatePerson(id,person);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePerson(@PathVariable int id){
        personService.deletePerson(id);
    }
}
