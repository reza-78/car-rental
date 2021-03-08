package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImp implements CustomerService{
    CustomerRepository repo;
    DriverRepository driverRepository;

    @Override
    public int create(Customer customer) {
        return repo.save(customer).getId();
    }

    @Override
    public Optional<Customer> get(int id) {
        return repo.findById(id);
    }

    @Override
    public int driverReserve(int customerId, int driverId) {
        Customer customer = repo.findById(customerId).get();
        Driver driver = driverRepository.findById(driverId).get();
        customer.setDriver(driver);
        repo.save(customer);
        return driver.getCarNumber();
    }
}
