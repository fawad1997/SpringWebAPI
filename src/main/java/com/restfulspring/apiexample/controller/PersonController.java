package com.restfulspring.apiexample.controller;

import com.restfulspring.apiexample.entity.Person;
import com.restfulspring.apiexample.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> addPerson(@Valid @RequestBody Person person, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
        }
        Person p = personService.addPerson(person);
        return new ResponseEntity<Person>(p, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public Person updatePerson(@PathVariable int id, @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePerson(@PathVariable int id){
        personService.deletePerson(id);
    }
}
