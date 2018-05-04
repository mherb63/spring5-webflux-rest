package com.demo.spring5webfluxrest.controller;

import com.demo.spring5webfluxrest.domain.Category;
import com.demo.spring5webfluxrest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.any;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;


    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void getAll() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Category1").build(),
                                      Category.builder().description("Category2").build()));

        webTestClient.get().uri("/category")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given((categoryRepository.findById("3")))
                .willReturn(Mono.just(Category.builder().description("Category3").build()));

        webTestClient.get().uri("/category/3")
                .exchange()
                .expectBody(Category.class);
    }
}