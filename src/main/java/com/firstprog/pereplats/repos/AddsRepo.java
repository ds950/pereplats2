package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Adds;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddsRepo extends JpaRepository<Adds, Long> {
    List<Adds> findAll();

    List<Adds> findAddsByPensid(Integer pensid);

    Iterable<Adds> findAllByUprcodeOrderByIdAsc(String uprcode);

    Adds findByPensid(Integer pensid);

    Adds deleteByPensid(Integer pensid);

}