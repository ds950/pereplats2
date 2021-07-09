package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Pensioner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Pensioner, Long> {
    List<Pensioner> findBySnl(String snl);
}
