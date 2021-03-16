package com.mohaymen.internship.carrental.control;

import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class CustomerController {
    private final CustomerService service;

    @PostMapping("/customer")
    public ResponseEntity<Integer> createCustomer(@RequestBody Customer newCustomer) {
        return new ResponseEntity<>(service.create(newCustomer), HttpStatus.CREATED);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable int id) {
        Optional<Customer> loaded = service.get(id);
        if (loaded.isPresent())
            return new ResponseEntity<>(loaded.get().responseModel(), HttpStatus.OK);
        throw new EntityNotFoundException(Customer.class.getName(), id);
    }

    @PostMapping("/customer/reserve/{customerId}")
    public ResponseEntity<Integer> driverReserve(@PathVariable int customerId) {
        return new ResponseEntity<>(service.driverReserve(customerId), HttpStatus.OK);
    }

}
