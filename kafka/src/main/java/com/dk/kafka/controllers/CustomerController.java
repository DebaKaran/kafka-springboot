package com.dk.kafka.controllers;

import com.dk.kafka.dtos.Customer;
import com.dk.kafka.services.CustomerProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-app")
public class CustomerController {

    @Autowired
    private CustomerProducer customerProducerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<String> sendCustomerEvent(@RequestBody Customer customer) {
        customerProducerService.sendCustomer(customer);
        return ResponseEntity.ok("Customer event sent to Kafka: " + customer.getName());
    }
}
