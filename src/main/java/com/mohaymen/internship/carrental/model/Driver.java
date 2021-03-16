package com.mohaymen.internship.carrental.model;

import com.mohaymen.internship.carrental.model.response.DriverResponseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Driver {
    @Id
    @GeneratedValue
    private int id;

    @ToString.Exclude
    @OneToOne(mappedBy = "driver", fetch = FetchType.LAZY)
    private Customer customer;

    private String name;
    private String lastName;
    @Setter
    private int carNumber;
    @Setter
    private boolean isFree = true;

    public DriverResponseModel responseModel() {
        return new DriverResponseModel(name, lastName, id, customer.getId(), carNumber, isFree);
    }
}
