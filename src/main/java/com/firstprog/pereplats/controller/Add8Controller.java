package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.*;
import com.firstprog.pereplats.repos.AddsRepo;
import com.firstprog.pereplats.repos.LogsRepo;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.firstprog.pereplats.domain.Inicial.getInitials;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

@Controller
public class Add8Controller {
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private LogsRepo logsRepo;
    HSSFCellStyle style1;
    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private void createSheetHeader(HSSFSheet sheet, int rowNum, Pensioner pensioner) throws ParseException {
        Row row = sheet.createRow(rowNum);
        Integer pensid = pensioner.getId();
        if (pensioner.getId() != null) {
            //иначе вносим в ячейку ФИО фамилию и инициалы имени и отчества
            row.createCell(0).setCellValue(pensioner.getPensnomer());
            row.getCell(0).setCellStyle(style1);
            row.createCell(1).setCellValue(pensioner.getFam() + " " + getInitials(pensioner.getIm()) + " " + getInitials(pensioner.getOtch()));
            row.getCell(1).setCellStyle(style1);
            //вносим номер выплатного дела
            row.createCell(2).setCellValue(pensioner.getNvd());
            row.getCell(2).setCellStyle(style1);//находим id пенсионера
            //ищем список документов по id пенсионера
            Adds adds = addsRepo.findByPensid(pensid);
            //если решение об обнаружении ошибки отсутствует, то поля остаются пустыми
            if (adds != null) {
                if (adds.getAdd1exist() == null) {
                    row.createCell(3).setCellValue("");
                    row.getCell(3).setCellStyle(style1);
                    row.createCell(4).setCellValue("");
                    row.getCell(4).setCellStyle(style1);
                    row.createCell(5).setCellValue("");
                    row.getCell(5).setCellStyle(style1);
                    row.createCell(6).setCellValue("");
                    row.getCell(6).setCellStyle(style1);
                } else { //если не пустые,  то...
                    //переводим дату в новый формат
                    String oldDateString = pensioner.getCalendar();
                    SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                    Date date = oldDateFormat.parse(oldDateString);
                    String result = newDateFormat.format(date);

                    //№ n/n - id набора документов
                    //Дата
                    row.createCell(3).setCellValue(adds.getAdd1reshdate());
                    row.getCell(3).setCellStyle(style1);
                    //номер набора документов
                    row.createCell(4).setCellValue(adds.getId());
                    row.getCell(4).setCellStyle(style1);
                    if (adds.getAdd1viptype().equals("1")) { //
                        row.createCell(5).setCellValue(Math.abs(Double.parseDouble(adds.getAdd1viprazn())));
                        row.getCell(5).setCellStyle(style1);
                        row.createCell(6).setCellValue("");
                        row.getCell(6).setCellStyle(style1);
                    } else {
                        row.createCell(6).setCellValue(Math.abs(Double.parseDouble(adds.getAdd1viprazn())));
                        row.getCell(6).setCellStyle(style1);
                        row.createCell(5).setCellValue("");
                        row.getCell(5).setCellStyle(style1);
                    }
                }

                if (adds.getAdd3exist() == null) {
                    row.createCell(11).setCellValue("");
                    row.getCell(11).setCellStyle(style1);
                    row.createCell(12).setCellValue("НЕТ");
                    row.getCell(12).setCellStyle(style1);
                } else {
                    row.createCell(11).setCellValue("ДА");
                    row.getCell(11).setCellStyle(style1);
                    row.createCell(12).setCellValue("");
                    row.getCell(12).setCellStyle(style1);
                }

                String protid = String.valueOf(adds.getId());

                if (adds.getAdd2exist() == null) {
                    row.createCell(7).setCellValue("");
                    row.getCell(7).setCellStyle(style1);
                    row.createCell(8).setCellValue("");
                    row.getCell(8).setCellStyle(style1);
                    row.createCell(9).setCellValue("");
                    row.getCell(9).setCellStyle(style1);
                    row.createCell(10).setCellValue("");
                    row.getCell(10).setCellStyle(style1);
                } else {
                    String oldDateString1 = adds.getAdd2protdate();
                    SimpleDateFormat oldDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat newDateFormat1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

                    Date date1 = oldDateFormat1.parse(oldDateString1);
                    String result1 = newDateFormat1.format(date1);
                    if (adds.getAdd2mistakeauthor() != null) {
                        if (adds.getAdd2mistakeauthor().equals("УПФР")) {
                            row.createCell(7).setCellValue(result1);
                            row.getCell(7).setCellStyle(style1);
                            row.createCell(8).setCellValue(protid);
                            row.getCell(8).setCellStyle(style1);
                            row.createCell(9).setCellValue("");
                            row.getCell(9).setCellStyle(style1);
                            row.createCell(10).setCellValue("ДА");
                            row.getCell(10).setCellStyle(style1);
                        } else {
                            row.createCell(7).setCellValue(result1);
                            row.getCell(7).setCellStyle(style1);
                            row.createCell(8).setCellValue(protid);
                            row.getCell(8).setCellStyle(style1);
                            row.createCell(9).setCellValue("ДА");
                            row.getCell(9).setCellStyle(style1);
                            row.createCell(10).setCellValue("");
                            row.getCell(10).setCellStyle(style1);
                        }

                    } else {
                        row.createCell(7).setCellValue(result1);
                        row.getCell(7).setCellStyle(style1);
                        row.createCell(8).setCellValue(protid);
                        row.getCell(8).setCellStyle(style1);
                        row.createCell(9).setCellValue("");
                        row.getCell(9).setCellStyle(style1);
                        row.createCell(10).setCellValue("");
                        row.getCell(10).setCellStyle(style1);

                    }
                }
            }
        }

    }
    @RequestMapping(value = "/getAdd8File.xls", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getPensFileXls(HttpServletRequest request, @AuthenticationPrincipal User user, String uprcode, HttpServletResponse response) throws ParseException {

        // создание самого excel файла в памяти
        HSSFWorkbook workbook = new HSSFWorkbook();
        style1 = workbook.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setWrapText(true);
        style1.setBorderTop(THIN);
        style1.setBorderBottom(THIN);
        style1.setBorderLeft(THIN);
        style1.setBorderRight(THIN);
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Журнал");
        // заполняем список какими-то данными
        List<Pensioner> pensionerList = pensRepo.findAllByUprcodeOrderByIdAsc(uprcode);
        String pensnomer = pensionerList.get(0).getPensnomer();
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,12));
        row.createCell(0).setCellValue("Журнал учета Протоколов о выявлении излишне выплаченных пенсионеру сумм пенсии");
        row.setHeight((short)600);
        row.getCell(0).setCellStyle(style1);

        // счетчик для строк
        rowNum = 1;
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 10));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 12));

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        row = sheet.createRow(rowNum);
        int width = (int) (8 * 1.14388) * 512; // 1757;
        int width1 = (int) (6 * 1.14388) * 512; // 1757;
        int width2 = (int) (2 * 1.14388) * 512; // 1757;
        row.createCell(0).setCellValue("№ n/n");
        sheet.setColumnWidth(0, width2);
        row.createCell(1).setCellValue("ФИО");
        sheet.setColumnWidth(1, width);
        row.createCell(2).setCellValue("Номер выплатного дела");
        sheet.setColumnWidth(2, width1);
        row.createCell(3).setCellValue("Решение об обнаружении ошибки, допущенной при установлении (выплате) пенсии");
        sheet.setColumnWidth(3, width1);
        row.createCell(4);
        row.createCell(5);
        row.createCell(6);
        row.createCell(7).setCellValue("Протокол о выявлении излишне выплаченных пенсионеру сумм пенсии");
        sheet.setColumnWidth(7, width1);
        row.createCell(8);
        row.createCell(9);
        row.createCell(10);
        row.createCell(11).setCellValue("Решение о взыскании сумм пенсии, излишне выплаченных пенсионеру");
        row.createCell(12);
        row.setHeight((short)800);
        sheet.setColumnWidth(11, width1);
        sheet.setColumnWidth(4, width1);
        sheet.setColumnWidth(5, width1);
        sheet.setColumnWidth(6, width1);
        sheet.setColumnWidth(8, width1);
        sheet.setColumnWidth(9, width1);
        sheet.setColumnWidth(10, width1);
        sheet.setColumnWidth(12, width1);
        ExcelStyle.rowStyle0_12(row, style1);

        rowNum = 2;
        row = sheet.createRow(rowNum);
        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        row.createCell(3).setCellValue("Дата");
        row.createCell(4).setCellValue("Номер");
        row.createCell(5).setCellValue("Переплата");
        row.createCell(6).setCellValue("Недоплата");
        row.createCell(7).setCellValue("Дата");
        row.createCell(8).setCellValue("Номер");
        row.createCell(9).setCellValue("Вина пенсионера");
        row.createCell(10).setCellValue("Вина УПФР");
        row.createCell(11).setCellValue("Да");
        row.createCell(12).setCellValue("Нет");
        row.setHeight((short)600);

        ExcelStyle.rowStyle0_12(row, style1);


        // заполняем лист данными
        for (Pensioner pensioner : pensionerList) {
            createSheetHeader(sheet, ++rowNum, pensioner);
        }


        // записываем созданный в памяти Excel документ в файл
        File outputFile = new File("C:\\temp\\povList.xls");
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Journal_protokol_o_vzisk.xls");

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Журнал учета протоколов о выявлении ИВСП / ADD8 / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}

