package com.restfulspring.apiexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryId")
    private int countryId;
    private String countryName;

    @OneToMany(targetEntity = City.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_countryId",referencedColumnName = "countryId")
    private List<City> cities;
}
