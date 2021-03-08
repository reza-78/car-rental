package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
