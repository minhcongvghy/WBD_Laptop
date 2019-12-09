package com.codegym.wbdlaptop.repository;

import com.codegym.wbdlaptop.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILineRepository extends JpaRepository<Line, Long> {
    Iterable<Line> findLinesByNameContaining(String line_name);
}
