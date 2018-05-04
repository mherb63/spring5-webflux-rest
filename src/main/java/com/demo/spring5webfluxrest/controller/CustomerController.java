package com.demo.spring5webfluxrest.controller;

import com.demo.spring5webfluxrest.domain.Customer;
import com.demo.spring5webfluxrest.repository.CustomerRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customer")
    Flux<Customer> getAll() {
        return customerRepository.findAll();
    }

    @GetMapping("customer/{id}")
    Mono<Customer> findById(@PathVariable String id) {
        return customerRepository.findById(id);
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<Customer> create(@RequestBody Publisher<Customer> customerPublisher) {
        return customerRepository.saveAll(customerPublisher);
    }

    @PutMapping("/customer/{id}")
    Mono<Customer> update(@RequestBody Customer customer, @PathVariable String id) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customer/{id}")
    Mono<Void> delete(@PathVariable String id) {
        return customerRepository.deleteById(id);
    }

    @PatchMapping("/customer/{id}")
    Mono<Customer> patch(@RequestBody Customer customer, @PathVariable String id) {
        Customer c = customerRepository.findById(id).block();
        if (! customer.getFirstName().equals(c.getFirstName())) {
            c.setFirstName(customer.getFirstName());
        }
        if (! customer.getLastName().equals(c.getLastName())) {
            c.setLastName(customer.getLastName());
        }
        return customerRepository.save(c);
    }

}
