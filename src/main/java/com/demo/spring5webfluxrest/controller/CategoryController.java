package com.demo.spring5webfluxrest.controller;

import com.demo.spring5webfluxrest.domain.Category;
import com.demo.spring5webfluxrest.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    Flux<Category> getAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    Flux<Category> create(@RequestBody Publisher<Category> categoryPublisher){
        return categoryRepository.saveAll(categoryPublisher);
    }

    @PutMapping("/category/{id}")
    Mono<Category> update(@RequestBody Category category, @PathVariable String id) {
        return categoryRepository.save(category);
    }

    @DeleteMapping("/category/{id}")
    Mono<Void> delete(@PathVariable String id) {
       return categoryRepository.deleteById(id);
    }

}
