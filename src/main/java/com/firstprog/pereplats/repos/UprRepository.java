package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Upr;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UprRepository extends CrudRepository<Upr, Long> {
    Iterable findAll();

    List<com.firstprog.pereplats.domain.Upr> findByUpr(String upr);

    List <Upr> findByUprcode (String uprcode);

}