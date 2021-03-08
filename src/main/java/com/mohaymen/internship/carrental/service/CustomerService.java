package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Customer;

import java.util.Optional;

public interface CustomerService {
    int create(Customer customer);
    Optional<Customer> get(int id);
    int driverReserve(int customerId, int driverId);
}
