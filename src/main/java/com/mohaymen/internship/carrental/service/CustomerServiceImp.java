package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.common.exception.NoDriverToAssignException;
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
    public int driverReserve(int customerId) {
        Customer customer = repo.findById(customerId).orElseThrow(() -> {
            log.warn("there is no customer with id {}", customerId);
            return new EntityNotFoundException(Customer.class.getName(), customerId);
        });

        Driver driver = findFirstFreeDriver().orElseThrow(() -> {
            log.warn("there is no driver to assign to customer with id {}", customerId);
            return new NoDriverToAssignException(customerId);
        });
        driver.setFree(false);
        driverRepository.save(driver);
        customer.setDriver(driver);
        repo.save(customer);
        return driver.getCarNumber();
    }

    private Optional<Driver> findFirstFreeDriver() {
        // Todo find more efficient way
        return driverRepository.findAll().stream().filter(Driver::isFree).findFirst();
    }
}
