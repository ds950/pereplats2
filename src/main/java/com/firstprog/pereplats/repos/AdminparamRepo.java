package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Adminparam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminparamRepo extends JpaRepository<Adminparam, Integer> {

    Adminparam findById (Long id);

}
