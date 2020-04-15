package com.restfulspring.apiexample.service;

import com.restfulspring.apiexample.entity.City;
import com.restfulspring.apiexample.entity.Country;
import com.restfulspring.apiexample.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public List<City> getCities(){
        return cityRepository.findAll();
    }
    public City getCity(int id){
        return cityRepository.findById(id).orElse(null);
    }
    public City addCity(City city){
        return cityRepository.save(city);
    }
    public City updateCity(int id,City city){
        city.setCityId(id);
        return cityRepository.save(city);
    }
    public void deleteCity(int id){
        cityRepository.deleteById(id);
    }
}
