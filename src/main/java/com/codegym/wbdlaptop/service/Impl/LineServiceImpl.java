package com.codegym.wbdlaptop.service.Impl;

import com.codegym.wbdlaptop.model.Line;
import com.codegym.wbdlaptop.repository.ILineRepository;
import com.codegym.wbdlaptop.service.ILineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LineServiceImpl implements ILineService {
    @Autowired
    private ILineRepository repository;

    @Override
    public Optional<Line> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Line> findAll() {
        return repository.findAll();
    }

    @Override
    public Line save(Line line) {
        return repository.save(line);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Line> findLinesByNameContaining(String line_name) {
        return repository.findLinesByNameContaining(line_name);
    }
}
