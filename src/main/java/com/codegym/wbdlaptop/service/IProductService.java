package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService {
    Optional<Product> findById(Long id);

    Iterable<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    void delete(Long id);

    Iterable<Product> findProductsByUserId(Long user_id);

    Iterable<Product> findProductsByLineId(Long line_id);

    Iterable<Product> findProductsByNameContaining(String name);

    Iterable<Product> findProductsByLineIdAndNameContaining(Long line_id, String name);
}
