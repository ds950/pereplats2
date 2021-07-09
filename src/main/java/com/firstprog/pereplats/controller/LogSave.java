package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.Logs;
import com.firstprog.pereplats.repos.LogsRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class LogSave {

    @Autowired
    private LogsRepo logsRepo;

    public void Logi(HttpServletResponse response) throws FileNotFoundException {
        OutputStream os = null;
        List<Logs> logsList = logsRepo.findAllByOrderByDateAsc();
        os = new FileOutputStream(new File("logs.txt"), true);

            response.setContentType("application/text");
            response.setHeader("Content-Disposition", "attachment; filename=Logs.txt");

            }
}