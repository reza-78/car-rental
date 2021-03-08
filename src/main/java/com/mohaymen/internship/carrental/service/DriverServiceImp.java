package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Driver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DriverServiceImp implements DriverService {
    DriverRepository repo;

    @Override
    public int create(Driver driver) {
        return repo.save(driver).getId();
    }

    @Override
    public Optional<Driver> get(int id) {
            return repo.findById(id);
    }
}
