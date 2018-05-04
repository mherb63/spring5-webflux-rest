package com.demo.spring5webfluxrest.controller;

import com.demo.spring5webfluxrest.domain.Category;
import com.demo.spring5webfluxrest.domain.Customer;
import com.demo.spring5webfluxrest.repository.CategoryRepository;
import com.demo.spring5webfluxrest.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class CustomerControllerTest {
    WebTestClient webTestClient;
    CustomerController customerController;
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerController = new CustomerController(customerRepository);
        webTestClient = WebTestClient.bindToController(customerController).build();
    }

    @Test
    public void getAll() {
        BDDMockito.given(customerRepository.findAll())
                .willReturn(Flux.just(Customer.builder().firstName("Mike").build(),
                        Customer.builder().lastName("Herb").build()));

        webTestClient.get().uri("/customer")
                .exchange()
                .expectBodyList(Customer.class)
                .hasSize(2);

    }

    @Test
    public void findById() {
        BDDMockito.given((customerRepository.findById("3")))
                .willReturn(Mono.just(Customer.builder().firstName("firstName").build()));

        webTestClient.get().uri("/customer/3")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void create() {
        BDDMockito.given(customerRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Customer.builder().firstName("firstName").build()));

        Mono<Customer> customerToSaveMono = Mono.just(Customer.builder().firstName("firstName").build());

        webTestClient.post()
                .uri("/customer")
                .body(customerToSaveMono, Customer.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

}