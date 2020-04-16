package com.restfulspring.apiexample.controller;

import com.restfulspring.apiexample.entity.Country;
import com.restfulspring.apiexample.repository.CountryRepository;
import com.restfulspring.apiexample.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<Country> getCountries(){
        return countryService.getCountries();
    }
    @GetMapping("/{id}")
    public Country getCountry(@PathVariable int id){
        return countryService.getCountry(id);
    }
    @PostMapping
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }
    @PutMapping("/{id}")
    public Country addCountry(@PathVariable int id,@RequestBody Country country){
        return countryService.updateCountry(id,country);
    }
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable int id){
        countryService.deleteCountry(id);
    }
}