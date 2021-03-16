package com.mohaymen.internship.carrental.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mohaymen.internship.carrental.common.exception.EntityNotFoundException;
import com.mohaymen.internship.carrental.common.exception.NoDriverToAssignException;
import com.mohaymen.internship.carrental.model.Customer;
import com.mohaymen.internship.carrental.model.Driver;
import com.mohaymen.internship.carrental.service.CustomerService;
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
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getCustomerTest() throws Exception {
        Mockito.when(service.get(Mockito.anyInt())).thenReturn(createCustomer());

        ObjectNode customerResponseModel = createCustomerResponseModel();

        mockMvc.perform(get("/customer/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(customerResponseModel.toString()));
    }

    @Test
    public void createCustomerTest() throws Exception {
        Customer customer = Customer.builder()
                .name("reza")
                .lastName("mehtari")
                .build();
        Mockito.when(service.create(Mockito.any(Customer.class))).thenReturn(2);
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("2"));
    }

    @Test
    public void driverReserveTest() throws Exception {
        Mockito.when(service.driverReserve(Mockito.anyInt())).thenReturn(10);
        mockMvc.perform(post("/customer/reserve/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("10"));

    }

    @Test
    public void getCustomer_customerNotExist() throws Exception {
        Mockito.when(service.get(Mockito.anyInt())).thenThrow(new EntityNotFoundException("customer", 1));

        mockMvc.perform(get("/customer/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("customer with id 1 not found"));
    }

    @Test
    public void reserveDriver_butNoDriverToAssign() throws Exception {
        Mockito.when(service.driverReserve(Mockito.anyInt())).thenThrow(new NoDriverToAssignException(1));

        mockMvc.perform(post("/customer/reserve/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().string("there is no driver for assigning to customer with id 1"));
    }

    private Optional<Customer> createCustomer() {
        return Optional.of(Customer.builder()
                .name("reza")
                .lastName("mehtari")
                .id(1)
                .driver(Driver.builder()
                        .id(2)
                        .build())
                .build());
    }

    private ObjectNode createCustomerResponseModel() {
        return mapper.createObjectNode()
                .put("name", "reza")
                .put("lastName", "mehtari")
                .put("id", 1)
                .put("driverId", 2);
    }
}
