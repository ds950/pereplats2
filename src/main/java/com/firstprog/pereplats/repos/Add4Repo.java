package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Add4;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Add4Repo extends JpaRepository<Add4, Long> {
    List<Add4> findAll();

    List<Add4> findByPensidOrderByViphismonthAsc(Integer pensid);

    List<Add4> findByPensidOrderByViphismonthDesc(Integer pensid);

    List<Add4> findByPensid (Integer pensid);

    List <Add4> findById(Integer id);

    Add4 findAdd4ByPensidOrderByViphismonthAsc(Integer pensid);
}
