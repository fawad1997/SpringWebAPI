package com.restfulspring.apiexample.repository;

import com.restfulspring.apiexample.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {

}
