package com.mohaymen.internship.carrental.service;

import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DriverServiceImpTest {

    @InjectMocks
    DriverServiceImp service;

    @Mock
    DriverRepository repository;

    @Test
    public void createDriverTest() {
        Driver driver = Driver.builder()
                .name("reza")
                .lastName("mehtari")
                .carNumber(2)
                .build();
        when(repository.save(any(Driver.class))).thenReturn(driver.toBuilder()
                .id(1)
                .isFree(true)
                .build());
        service.create(driver);

        verify(repository, times(1)).save(any(Driver.class));
    }

    @Test
    public void getDriverTest_whenReserved() {
        when(service.get(1)).thenReturn(Optional.of(createDriver().toBuilder()
                .customer(Customer.builder()
                    .id(3)
                    .build())
                .isFree(false)
                .build()));
        Optional<Driver> result = service.get(1);

        assertTrue(result.isPresent());
        Driver driver = result.get();
        assertDriver(driver);
        assertEquals(3, driver.getCustomer().getId());
        assertFalse(driver.isFree());
    }

    @Test
    public void getDriverTest_whenNotReserved() {
        when(service.get(1)).thenReturn(Optional.of(createDriver()));
        Optional<Driver> result = service.get(1);

        assertTrue(result.isPresent());
        Driver driver = result.get();
        assertDriver(driver);
        assertNull(driver.getCustomer());
        assertTrue(driver.isFree());
    }

    private Driver createDriver() {
        return Driver.builder()
                .name("reza")
                .lastName("mehtari")
                .carNumber(2)
                .id(1)
                .isFree(true)
                .build();
    }

    private void assertDriver(Driver driver) {
        assertEquals("reza", driver.getName());
        assertEquals("mehtari", driver.getLastName());
        assertEquals(1, driver.getId());
        assertEquals(2, driver.getCarNumber());
    }
}