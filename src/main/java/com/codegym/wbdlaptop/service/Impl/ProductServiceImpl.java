package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.repository.IProductRepository;
import com.codegym.wbdlaptop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository repository;

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Product> findProductsByUserId(Long user_id) {
        return repository.findProductsByUserId(user_id);
    }

    @Override
    public Iterable<Product> findProductsByLineId(Long line_id) {
        return repository.findProductsByLineId(line_id);
    }

    @Override
    public Iterable<Product> findProductsByNameContaining(String name) {
        return repository.findProductsByNameContaining(name);
    }

    @Override
    public Iterable<Product> findProductsByLineIdAndNameContaining(Long line_id, String name) {
        return repository.findProductsByLineIdAndNameContaining(line_id, name);
    }
}
