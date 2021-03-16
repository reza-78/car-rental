package com.mohaymen.internship.carrental.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import com.mohaymen.internship.carrental.service.DriverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DriverService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getDriverTest() throws Exception {
        Mockito.when(service.get(1)).thenReturn(createDriver());
        ObjectNode driverResponseModel = createDriverResponseModel();

        mockMvc.perform(get("/driver/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(driverResponseModel.toString()));

    }

    @Test
    public void createDriverTest() throws Exception {
        Mockito.when(service.create(Mockito.any(Driver.class))).thenReturn(2);
        Driver driver = Driver.builder()
                .name("reza")
                .lastName("mehtari")
                .carNumber(2)
                .build();
        mockMvc.perform(post("/driver")
                .content(mapper.writeValueAsString(driver))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("2"));
    }

    @Test
    public void getDriver_driverNotExists() throws Exception {
        Mockito.when(service.get(Mockito.anyInt())).thenThrow(new EntityNotFoundException("driver", 1));

        mockMvc.perform(get("/driver/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("driver with id 1 not found"));
    }

    private Optional<Driver> createDriver() {
        return Optional.of(Driver.builder()
                .name("reza")
                .lastName("mehtari")
                .id(1)
                .carNumber(2)
                .customer(Customer.builder()
                        .id(3)
                        .build())
                .isFree(false)
                .build());
    }

    private ObjectNode createDriverResponseModel() {
        return mapper.createObjectNode()
                .put("name", "reza")
                .put("lastName", "mehtari")
                .put("id", 1)
                .put("carNumber", 2)
                .put("customerId", 3)
                .put("free", false);
    }
}