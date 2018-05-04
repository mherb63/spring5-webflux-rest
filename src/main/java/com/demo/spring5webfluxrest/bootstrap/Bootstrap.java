package com.demo.spring5webfluxrest.bootstrap;

import com.demo.spring5webfluxrest.domain.Category;
import com.demo.spring5webfluxrest.domain.Customer;
import com.demo.spring5webfluxrest.repository.CategoryRepository;
import com.demo.spring5webfluxrest.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories() {
        if (categoryRepository.count().block() != 0) {
            categoryRepository.deleteAll().block();
        }

        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Dried").build()).block();
        categoryRepository.save(Category.builder().description("Fresh").build()).block();
        categoryRepository.save(Category.builder().description("Exotic").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();

        log.debug("Created Category count: " + categoryRepository.count().block());
    }

    private void loadCustomers() {
        // clean up old data
        if (customerRepository.count().block() != 0) {
            customerRepository.deleteAll().block();
        }
        customerRepository.save((Customer.builder().firstName("Joe").lastName("Newman").build())).block();
        customerRepository.save((Customer.builder().firstName("Michael").lastName("Lachappele").build())).block();
        customerRepository.save((Customer.builder().firstName("David").lastName("Winter").build())).block();
        customerRepository.save((Customer.builder().firstName("Anne").lastName("Newman").build())).block();
        customerRepository.save((Customer.builder().firstName("Joe").lastName("Hine").build())).block();

        log.debug("Created Customer count: " + customerRepository.count().block());
    }
}
