package com.firstprog.pereplats.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LogToFile {
    public static void Logi(String loggs) {
        OutputStream os = null;
        try {
            //в конструкторе FileOutputStream используем флаг true, который обозначает обновление содержимого файла
            os = new FileOutputStream(new File("logs.txt"), true);
            os.write(loggs.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}