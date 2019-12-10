package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends PagingAndSortingRepository<Product, Long> {
    Iterable<Product> findProductsByUserId(Long user_id);
    Iterable<Product> findProductsByNameContaining(String name);
    Iterable<Product> findProductsByLineId(Long line_id);
    Iterable<Product> findProductsByLineIdAndNameContaining(Long line_id, String name);
}
