package com.firstprog.pereplats.repos;

import com.firstprog.pereplats.domain.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogsRepo extends JpaRepository<Logs, Integer> {

        List<Logs> findAllByOrderByDateAsc();

        List<Logs> findByUsername (String username);

        void deleteAll();

    }
