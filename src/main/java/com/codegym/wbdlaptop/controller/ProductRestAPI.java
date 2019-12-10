package com.codegym.wbdlaptop.controller;

import com.codegym.wbdlaptop.message.request.SearchProductByLineAndName;
import com.codegym.wbdlaptop.message.request.SearchProductByNameForm;
import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class ProductRestAPI {

    @Autowired
    private IProductService productService;

    @GetMapping("/product/pagination")
    public ResponseEntity<?> getListProductAndPagination(@PageableDefault(value = 2 , sort = "date" ,direction = Sort.Direction.ASC) Pageable pageable) {
//        DESC = Old , ASC = new
        Page<Product> products =  productService.findAll(pageable);

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<?> getListProduct() {
        List<Product> products = (List<Product>) productService.findAll();
        if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {

        product.setUpdate(false);
        productService.save(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        Optional<Product> product1 = productService.findById(id);

        if (!product1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product1.get().setName(product.getName());
        product1.get().setCpu(product.getCpu());
        product1.get().setRam(product.getRam());
        product1.get().setPrice(product.getPrice());
        product1.get().setDescription(product.getDescription());
        product1.get().setUpdate(true);
        product1.get().setLine(product.getLine());
        product1.get().setUser(product.getUser());

        productService.save(product1.get());

        return new ResponseEntity<>(product1, HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/product/search-by-name")
    public ResponseEntity<?> searchProductByName(@RequestBody SearchProductByNameForm nameForm) {
        if (nameForm.getName() == "" || nameForm.getName() == null ) {
            List<Product> products = (List<Product>) productService.findAll();

            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(products,HttpStatus.OK);
            }
        }

        List<Product> products = (List<Product>) productService.findProductsByNameContaining(nameForm.getName());
        if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

    @GetMapping("/product/search-by-lineId/{id}")
    public ResponseEntity<?> searchByLineId(@PathVariable Long id) {
        List<Product> products = (List<Product>) productService.findProductsByLineId(id);

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/product/search-by-line-and-name")
    public ResponseEntity<?> searchProductByLineAndName(@RequestBody SearchProductByLineAndName searchForm) {
        if (searchForm.getName() == null && searchForm.getLineId() == null) {
            List<Product> products = (List<Product>) productService.findAll();
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        }

        if (searchForm.getName() == null && searchForm.getLineId() != null) {
            List<Product> products = (List<Product>) productService.findProductsByLineId(searchForm.getLineId());
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        }

        if (searchForm.getName() != null && searchForm.getLineId() == null) {
            List<Product> products = (List<Product>) productService.findProductsByNameContaining(searchForm.getName());
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        }

        if (searchForm.getLineId() != null && searchForm.getName() != null) {
            List<Product> products = (List<Product>) productService.findProductsByLineIdAndNameContaining(searchForm.getLineId(),searchForm.getName());
            if(products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
