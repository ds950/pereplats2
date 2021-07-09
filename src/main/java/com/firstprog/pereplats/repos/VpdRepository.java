package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.model.Vpd;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VpdRepository extends CrudRepository<Vpd, Long> {
    Iterable<Vpd> findAll();

    List<Vpd> findByVpd(String vpd);

}
