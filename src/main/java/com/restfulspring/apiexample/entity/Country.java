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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_fk",referencedColumnName = "countryId")
    private List<City> cities;
}
