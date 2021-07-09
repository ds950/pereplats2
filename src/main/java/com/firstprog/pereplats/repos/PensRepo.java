package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Pensioner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PensRepo extends JpaRepository<Pensioner, Long> {
    List<Pensioner> findAll();

    List<Pensioner> findAllByUprcodeOrderByIdAsc(String uprcode);

    List<Pensioner> findByFio(String fio);

    Pensioner findBySnl (String snl);

    List <Pensioner> findBySnlOrderByIdDesc(String snl);

    Pensioner findById(int id);

    List <Pensioner> findPensionerById(Integer id);

    List <Pensioner> findByIdOrderByIdAsc(Integer id);

}