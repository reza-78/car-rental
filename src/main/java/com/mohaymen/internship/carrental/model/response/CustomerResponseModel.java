package com.mohaymen.internship.carrental.model.response;

import lombok.Value;

@Value
public class CustomerResponseModel {
    String name;
    String lastName;
    int id;
    int driverId;
}
