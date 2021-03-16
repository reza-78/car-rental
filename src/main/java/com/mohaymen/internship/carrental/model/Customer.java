package com.mohaymen.internship.carrental.model;

import com.mohaymen.internship.carrental.model.response.CustomerResponseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private int id;

    @ToString.Exclude
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Driver driver;

    private String name;
    private String lastName;

    public CustomerResponseModel responseModel() {
        return new CustomerResponseModel(name, lastName, id, driver.getId());
    }
}
