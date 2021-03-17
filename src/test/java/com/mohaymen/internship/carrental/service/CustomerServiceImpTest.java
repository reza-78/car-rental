package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImpTest {

    @InjectMocks
    CustomerServiceImp service;

    @Mock
    CustomerRepository repository;

    @Mock
    DriverRepository driverRepository;

    @Captor
    ArgumentCaptor<Customer> captor;

    @Test
    public void createCustomerTest() {
        Customer customer = Customer.builder()
                .name("reza")
                .lastName("mehtari")
                .build();
        when(repository.save(any(Customer.class))).thenReturn(customer.toBuilder()
                .id(1)
                .build());
        service.create(customer);
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    public void getCustomerTest_whenNoDriverAssigned() {
        when(service.get(1)).thenReturn(Optional.of(createCustomer()));
        Optional<Customer> result = service.get(1);

        assertTrue(result.isPresent());
        Customer customer = result.get();
        assertCustomer(customer);
        assertNull(customer.getDriver());
    }

    @Test
    public void getCustomerTest_whenDriverAssigned() {
        when(service.get(1)).thenReturn(Optional.of(createCustomer().toBuilder()
                .driver(Driver.builder()
                        .id(2)
                        .build())
                .build()));
        Optional<Customer> result = service.get(1);

        assertTrue(result.isPresent());
        Customer customer = result.get();
        assertCustomer(customer);
        assertEquals(2, customer.getDriver().getId());

    }

    @Test
    public void reserveTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(createCustomer()));
        when(driverRepository.findAll()).thenReturn(Collections.singletonList(createDriver().toBuilder()
                .isFree(true)
                .build()));

        service.driverReserve(1);
        verify(driverRepository).save(any(Driver.class));
        verify(repository).save(captor.capture());

        Driver savedDriver = captor.getValue().getDriver();
        assertEquals(2, savedDriver.getId());
        assertEquals(3, savedDriver.getCarNumber());
        assertFalse(savedDriver.isFree());
    }

    private Customer createCustomer() {
        return Customer.builder()
                .name("reza")
                .lastName("mehtari")
                .id(1)
                .build();
    }

    private Driver createDriver() {
        return Driver.builder()
                .name("ali")
                .lastName("goli")
                .id(2)
                .carNumber(3)
                .isFree(true)
                .build();
    }

    private void assertCustomer(Customer customer) {
        assertEquals("reza", customer.getName());
        assertEquals("mehtari", customer.getLastName());
        assertEquals(1, customer.getId());
    }
}