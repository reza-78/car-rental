package com.mohaymen.internship.carrental.model.response;

import lombok.Value;

@Value
public class DriverResponseModel {
    String name;
    String lastName;
    int id;
    int customerId;
    int carNumber;
    boolean isFree;
}
