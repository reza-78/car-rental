package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Driver;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DriverService {
    int create(Driver driver);
    Optional<Driver> get(int id) throws Exception;
}
