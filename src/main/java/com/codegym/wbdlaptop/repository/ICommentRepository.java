package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    Iterable<Comment> findCommentsByProductId(Long diary_id);
}
