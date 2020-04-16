package com.restfulspring.apiexample.service;

import com.restfulspring.apiexample.entity.Country;
import com.restfulspring.apiexample.exception.NotFoundException;
import com.restfulspring.apiexample.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getCountries(){
        return countryRepository.findAll();
    }
    public Country getCountry(int id){
        Optional<Country> country = countryRepository.findById(id);
        if(!country.isPresent())
                throw new NotFoundException("Country not found!");
        return country.get();
    }
    public Country addCountry(Country country){
        return countryRepository.save(country);
    }
    public Country updateCountry(int id,Country country){
        country.setCountryId(id);
        return countryRepository.save(country);
    }
    public void deleteCountry(int id){
        countryRepository.deleteById(id);
    }
}
