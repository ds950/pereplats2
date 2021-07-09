package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.ExcelStyle;
import com.firstprog.pereplats.domain.Pensioner;
import com.firstprog.pereplats.repos.PensRepo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

@Controller
public class ExcelPensController {
    @Autowired
    private PensRepo pensRepo;
    HSSFCellStyle style1;
    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private static void createSheetHeader(HSSFSheet sheet, int rowNum, Pensioner pensioner) {
        Row row = sheet.createRow(rowNum);

        row.createCell(0).setCellValue(pensioner.getFio());
        row.createCell(2).setCellValue(pensioner.getSnl());
        row.createCell(4).setCellValue(pensioner.getNvd());
        row.createCell(6).setCellValue(pensioner.getVp());
        row.createCell(8).setCellValue(pensioner.getVpd());
        row.createCell(10).setCellValue(pensioner.getCalendar());

    }
    @RequestMapping(value = "/getPensFile.xls", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getPensFileXls(HttpServletResponse response) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        style1 = workbook.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setWrapText(true);
        style1.setBorderTop(THIN);
        style1.setBorderBottom(THIN);
        style1.setBorderLeft(THIN);
        style1.setBorderRight(THIN);
        // создание самого excel файла в памяти
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Список пенсионеров");
        // заполняем список какими-то данными
        Iterable<Pensioner> pensionerList = pensRepo.findAll();

        // счетчик для строк
        int rowNum = 0;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("ФИО");
        row.createCell(1);
        row.createCell(2).setCellValue("СНИЛС");
        row.createCell(3);
        row.createCell(4).setCellValue("Номер выплатного дела");
        row.createCell(5);
        row.createCell(6).setCellValue("Вид пенсии");
        row.createCell(7);
        row.createCell(8).setCellValue("Вид пенсионного действия");
        row.createCell(9);
        row.createCell(10).setCellValue("Дата установления с ошибкой");
        row.createCell(11);
        ExcelStyle.rowStyle0_11(row, style1);
        // заполняем лист данными
        for (Pensioner pensioner : pensionerList) {
            createSheetHeader(sheet, ++rowNum, pensioner);
        }

        // записываем созданный в памяти Excel документ в файл
        File outputFile = new File("C:\\temp\\pensList.xls");
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=pensioners.xls");

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}

