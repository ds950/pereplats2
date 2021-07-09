package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.Adminparam;
import com.firstprog.pereplats.repos.AdminparamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdminparamService {

    @Autowired
    AdminparamRepo adminparamRepo;

    @Transactional
    public void save(Adminparam adminparam) {
        adminparamRepo.save(adminparam);
    }

    public Adminparam findByAdminparam() {
        return adminparamRepo.findById(1l);
    }
}