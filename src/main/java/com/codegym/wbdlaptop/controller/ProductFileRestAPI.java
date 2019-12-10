package com.codegym.wbdlaptop.controller;

import com.codegym.wbdlaptop.message.request.FileForm;
import com.codegym.wbdlaptop.message.response.ResponseMessage;
import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.model.User;
import com.codegym.wbdlaptop.service.IProductService;
import com.codegym.wbdlaptop.service.IUserService;
import com.codegym.wbdlaptop.service.Impl.ProductFirebaseServiceImpl;
import com.codegym.wbdlaptop.service.Impl.UserFirebaseServiceExtends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class ProductFileRestAPI {
    @Autowired
    private IProductService productService;

    @Autowired
    private ProductFirebaseServiceImpl firebaseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserFirebaseServiceExtends userFirebaseServiceExtends;

    @Autowired
    Environment env;

    @PostMapping(value = "/file/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> uploadFile(@ModelAttribute FileForm fileForm, BindingResult result, @PathVariable Long id) throws IOException {

        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(new ResponseMessage("Upload File Fail!"), HttpStatus.BAD_REQUEST);
            }
            MultipartFile multipartFile = fileForm.getFile();
            Optional<Product> product = productService.findById(id);
            if (!product.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (multipartFile != null) {
                if (product.get().getBlobString() == null) {
                    String urlFile = firebaseService.saveToFirebaseStorage(product.get(), multipartFile);
                    product.get().setUrlFile(urlFile);
                } else {
                    firebaseService.deleteFirebaseStorageFile(product.get());
                    String urlFile = firebaseService.saveToFirebaseStorage(product.get(), multipartFile);
                    product.get().setUrlFile(urlFile);
                }
            }
            productService.save(product.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e ,  HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/user-avatar/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> uploadAvatarUser(@ModelAttribute FileForm fileForm, BindingResult result, @PathVariable Long id) throws IOException {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(new ResponseMessage("Upload avatar user fail"), HttpStatus.BAD_REQUEST);
            }
            MultipartFile multipartFile = fileForm.getFile();
            Optional<User> user = userService.findById(id);

            if (!user.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (multipartFile != null) {
                if(user.get().getBlobString() == null) {
                    String urlFile = userFirebaseServiceExtends.saveToFirebaseStorage(user.get() , multipartFile);
                    user.get().setAvatar(urlFile);
                } else {
                    userFirebaseServiceExtends.deleteFirebaseStorageFile(user.get());
                    String urlFile = userFirebaseServiceExtends.saveToFirebaseStorage(user.get() , multipartFile);
                    user.get().setAvatar(urlFile);
                }
            }
            userService.save(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
