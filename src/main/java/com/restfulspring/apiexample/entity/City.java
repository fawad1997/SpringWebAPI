package com.restfulspring.apiexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityId")
    private int cityId;
    private String cityName;
    @Column(name = "fk_countryId")
    private int fk_countryId;
}
