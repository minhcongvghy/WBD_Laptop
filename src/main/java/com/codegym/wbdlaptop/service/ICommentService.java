package com.codegym.wbdlaptop.service;

import com.codegym.wbdlaptop.model.Comment;

import java.util.Optional;

public interface ICommentService {
    Optional<Comment> findById(Long id);

    Iterable<Comment> findAll();

    Comment save(Comment tag);

    void delete(Long id);

    Iterable<Comment> findCommentsByProductId(Long product_id);
}
