package com.codegym.wbdlaptop.controller;

import com.codegym.wbdlaptop.model.Comment;
import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.service.ICommentService;
import com.codegym.wbdlaptop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")

public class CommentRestAPI {
    @Autowired
    private ICommentService commentService;

    @Autowired
    private IProductService productService;

    @GetMapping("/comment")
    public ResponseEntity<?> getListAllComment() {
        List<Comment> comments = (List<Comment>) commentService.findAll();

        if(comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);

        if(comment.isPresent()) {
            return new ResponseEntity<>(comment,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        Optional<Product> product = productService.findById(comment.getIdProduct());
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String date = now.format(format);

            comment.setEdit(false);
            comment.setDate(date);
            comment.setProduct(product.get());

            commentService.save(comment);

            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> editComment(@PathVariable Long id, @RequestBody Comment comment) {
        Optional<Comment> comment1 = commentService.findById(id);

        if (!comment1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String date = now.format(format);

        comment1.get().setEdit(true);
        comment1.get().setDate(date);
        comment1.get().setContent(comment.getContent());

        commentService.save(comment1.get());

        return new ResponseEntity<>(comment1,HttpStatus.OK);

    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            commentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/comment/product/{id}")
    public ResponseEntity<?> getAllCommentByProductId(@PathVariable Long id) {
        List<Comment> comments = (List<Comment>) commentService.findCommentsByProductId(id);

        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comments,HttpStatus.OK);
    }
}
