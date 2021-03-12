package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
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
        Customer customer = repo.findById(customerId).orElseThrow(() -> {
            log.warn("there is no customer with id {}", customerId);
            return new EntityNotFoundException(Customer.class.getName(), customerId);
        });

        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> {
            log.warn("there is no driver with id {}", driverId);
            return new EntityNotFoundException(Driver.class.getName(), driverId);
        });
        customer.setDriver(driver);
        repo.save(customer);
        return driver.getCarNumber();
    }
}
