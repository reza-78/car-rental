package com.mohaymen.internship.carrental.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Driver {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne(mappedBy = "driver")
    private Customer customer;

    private String name;
    private String lastName;
    @Setter
    private int carNumber;

}
