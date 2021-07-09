package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.model.Klass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KlassRepo extends CrudRepository<Klass, Long> {
    Iterable<Klass> findAll();

    List<Klass> findKlassByStatja(String statja);
}