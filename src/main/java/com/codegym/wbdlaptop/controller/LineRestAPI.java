package com.codegym.wbdlaptop.controller;

import com.codegym.wbdlaptop.message.request.SearchLineByNameLine;
import com.codegym.wbdlaptop.model.Line;
import com.codegym.wbdlaptop.model.Product;
import com.codegym.wbdlaptop.service.ILineService;
import com.codegym.wbdlaptop.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class LineRestAPI {
    @Autowired
    private ILineService lineService;

    @Autowired
    private IProductService productService;

    @GetMapping("/line")
    public ResponseEntity<?> getListAllLine() {
        List<Line> lineList = (List<Line>) lineService.findAll();

        if(lineList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(lineList,HttpStatus.OK);
    }

    @GetMapping("/line/{id}")
    public ResponseEntity<?> getLine(@PathVariable Long id) {
        Optional<Line> line = lineService.findById(id);

        if (!line.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(line,HttpStatus.OK);
    }

    @PostMapping("/line")
    public ResponseEntity<?> createLine(@Valid @RequestBody Line line) {
        lineService.save(line);

        return new ResponseEntity<>(line,HttpStatus.CREATED);
    }

    @PutMapping("/line/{id}")
    public ResponseEntity<?> updateLine(@Valid @RequestBody Line line, @PathVariable Long id) {
        Optional<Line> line1 = lineService.findById(id);
        if(!line1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        line1.get().setName(line.getName());
        lineService.save(line1.get());

        return new ResponseEntity<>(line1,HttpStatus.OK);
    }

    @DeleteMapping("/line/{id}")
    public ResponseEntity<?> deleteLine(@PathVariable Long id) {
        Optional<Line> line = lineService.findById(id);
        if(!line.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Product> products = (List<Product>) productService.findProductsByUserId(id);

        if(!products.isEmpty()) {
            for (Product product: products) {
                productService.delete(product.getId());
            }
        }

        lineService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/line/search-by-name")
    public ResponseEntity<?> searchLineByNameLine(@RequestBody SearchLineByNameLine lineForm) {
        if(lineForm.getName() == "" || lineForm.getName() == null ) {
            List<Line> lines = (List<Line>) lineService.findAll();
            if(lines.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(lines,HttpStatus.OK);
            }
        }

        List<Line> lines = (List<Line>) lineService.findLinesByNameContaining(lineForm.getName());
        if(lines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(lines,HttpStatus.OK);
        }
    }
}
