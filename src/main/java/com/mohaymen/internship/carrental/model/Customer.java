package com.mohaymen.internship.carrental.model;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    private int id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Driver driver;

    private String name;
    private String lastName;
}
