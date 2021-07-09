package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.model.Dolzhnost;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DolzhnostRepo extends CrudRepository<Dolzhnost, Long> {
    Iterable<Dolzhnost> findAll();

    List<Dolzhnost> findByDolzhnost(String dolzhnost);
}