package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.model.Vp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VpRepository extends CrudRepository<Vp, Long> {
    Iterable<Vp> findAll();

    List<Vp> findByVp(String vp);
}