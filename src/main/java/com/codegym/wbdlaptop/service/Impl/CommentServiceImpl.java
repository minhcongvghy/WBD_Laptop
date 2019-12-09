package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Comment;
import com.codegym.wbdlaptop.repository.ICommentRepository;
import com.codegym.wbdlaptop.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private ICommentRepository repository;
    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Comment save(Comment line) {
        return repository.save(line);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Comment> findCommentsByProductId(Long product_id) {
        return repository.findCommentsByProductId(product_id);
    }
}
