package com.devpb98.controller;

import com.devpb98.entity.Product;
import com.devpb98.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @PostMapping("/product")
    public Product save(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/products")
    public List<Product> getAll(){
        return repository.findAll();
    }
}
