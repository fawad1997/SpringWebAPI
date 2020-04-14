package com.restfulspring.apiexample.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "ID")
    private int ID;
    @JsonProperty(value = "Name")
    private String Name;
    @JsonProperty(value = "Age")
    private int Age;
    @JsonProperty(value = "Height")
    private double Height;
    @JsonProperty(value = "CNIC")
    private String CNIC;
}
