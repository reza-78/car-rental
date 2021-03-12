package com.mohaymen.internship.carrental.control;

import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.model.Driver;
import com.mohaymen.internship.carrental.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class DriverController {
    DriverService service;

    @PostMapping("/driver")
    public ResponseEntity<Integer> createDriver(@RequestBody Driver newDriver) {
        return new ResponseEntity<>(service.create(newDriver), HttpStatus.OK);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<?> getDriver(@PathVariable int id) throws Exception {
        Optional<Driver> loaded = service.get(id);
        if (loaded.isPresent())
            return new ResponseEntity<>(loaded.get(), HttpStatus.OK);
        throw new EntityNotFoundException(Driver.class.getName(), id);
    }
}
