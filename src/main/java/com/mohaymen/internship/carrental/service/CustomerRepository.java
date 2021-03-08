package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
