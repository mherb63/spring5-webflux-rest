package com.demo.spring5webfluxrest.repository;

import com.demo.spring5webfluxrest.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
