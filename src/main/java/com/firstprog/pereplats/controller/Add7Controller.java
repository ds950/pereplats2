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
import java.util.Locale;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

@Controller
public class Add7Controller {
    HSSFCellStyle style1;
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private LogsRepo logsRepo;

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private void createSheetHeader(HSSFSheet sheet, int rowNum, Adds adds) throws ParseException {
        if (adds.getAdd3exist() != null){
                Row row = sheet.createRow(rowNum);
                //дату в новый вид
                String oldDateString = adds.getAdd3date();
                SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

                Date date = oldDateFormat.parse(oldDateString);
                String result = newDateFormat.format(date);

                //id в строчный формат для печати
                String strid = String.valueOf(adds.getId());
                Integer pid = adds.getPensid();
                Pensioner pensioner = pensRepo.findById(pid);
                String pensnomer = pensioner.getPensnomer();

                row.createCell(0).setCellValue(pensnomer);
                int width = (int) (6 * 1.14388) * 512; // 1757;
                row.createCell(1).setCellValue(adds.getBlock1fio() + adds.getBlock2fio()); //ФИО
                row.createCell(2).setCellValue(adds.getBlock1vd() + adds.getBlock2vd()); //№ ВД
                row.createCell(3).setCellValue(result); //новая дата
                row.createCell(4).setCellValue(strid); //id
                //заполнение листа данными
                double razmerTverd;
                int razmerProc;

                if(adds.getBlock3razmer().isEmpty()){
                    razmerProc = 0;
                } else {
                razmerProc = Integer.parseInt(adds.getBlock3razmer());
                }

                if(adds.getBlock3razmertverd().isEmpty()){
                    razmerTverd = 0;
                } else {
                    razmerTverd = Double.parseDouble(adds.getBlock3razmertverd());
                }

                double razmer = razmerProc + razmerTverd; //одно из полей пустое всегда, поэтому делаем их вместе

                String razmerProcStr = razmer + "%"; //если результат процентный, до добавляем в конец значения процент
                // если блок 1 пустой и размер в процентах равен 0, то данные пишутся в ячейку УДЕРЖАНИЕ без процента
                if (adds.getBlock1fio().equals("") && adds.getBlock3razmer().isEmpty()) {
                    row.createCell(6).setCellValue("");
                    row.createCell(5).setCellValue(razmer);
                    // если блок 1 пустой и твердый размер равен 0, то данные пишутся в ячейку УДЕРЖАНИЕ с процентом
                } else if (adds.getBlock1fio().equals("") && adds.getBlock3razmertverd().isEmpty()) {
                    row.createCell(5).setCellValue(razmerProcStr);
                    row.createCell(6).setCellValue("");
                    //если блок 2 пуст и процентный размер равен 0, то данные пишутся в ячейку ВОЗМЕЩЕНИЕ без процента
                } else if (adds.getBlock2fio().equals("") && adds.getBlock3razmer().isEmpty()) {
                    row.createCell(5).setCellValue("");
                    row.createCell(6).setCellValue(razmer);
                } else {
                    //если блок 2 пуст и твердый размер равен 0, то данные пишутся в ячейку ВОЗМЕЩЕНИЕ с процентом
                    row.createCell(6).setCellValue(razmerProcStr);
                    row.createCell(5).setCellValue("");
                }
                //стиль для ячеек
                ExcelStyle.rowStyle0_6(row, style1);
                //row.getCell(7).setCellStyle(style1);
                //row.createCell(6).setCellValue(add3.getBlock3_razmer()+ add3.getBlock3_razmer_tverd());

            }
    }


    @RequestMapping(value = "/getAdd7File.xls" +
            "", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getAdd7FileXls(HttpServletRequest request, @AuthenticationPrincipal User user, HttpServletResponse response, String uprcode) throws ParseException {

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

        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setWrapText(true);
        style2.setBorderTop(THIN);
        style2.setBorderBottom(THIN);
        style2.setBorderLeft(THIN);
        style2.setBorderRight(THIN);

        HSSFCellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HorizontalAlignment.CENTER);
        style3.setVerticalAlignment(VerticalAlignment.CENTER);

        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Журнал");
        // заполняем список какими-то данными
        Iterable<Adds> addsList = addsRepo.findAllByUprcodeOrderByIdAsc(uprcode);

        // счетчик для строк
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 6));

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        int i = 0;
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Журнал учета Решений о взыскании сумм пенсии, излишне выплаченных пенсионеру");
        sheet.getMergedRegion(0);
        row.setHeight((short)600);//высота строки
        row.getCell(0).setCellStyle(style3); //стиль ячейки

        rowNum = 1;
        int width = (int) (2 * 1.14388) * 512; // 1757;
        int width1 = (int) (20 * 1.14388) * 512; // 1757;
        int width2 = (int) (6 * 1.14388) * 512; // 1757;
        row = sheet.createRow(rowNum);
        row.setHeight((short)600);
        row.createCell(0).setCellValue("№ n/n ");
        row.getCell(0).setCellStyle(style1);
        sheet.setColumnWidth(0, width);
        row.createCell(1).setCellValue("ФИО");
        row.getCell(1).setCellStyle(style1);
        sheet.setColumnWidth(1, width1);
        row.createCell(2).setCellValue("Номер выплатного дела");
        row.getCell(2).setCellStyle(style1);
        sheet.setColumnWidth(2, width2);
        row.createCell(3).setCellValue("Решение о взыскании сумм пенсии, излишне выплаченных пенсионеру");
        row.getCell(3).setCellStyle(style1);
        sheet.setColumnWidth(3, width2);
        row.createCell(4);
        row.getCell(4).setCellStyle(style1);
        sheet.setColumnWidth(4, width2);
        row.createCell(5);
        row.getCell(5).setCellStyle(style1);
        sheet.setColumnWidth(5, width2);
        row.createCell(6);
        row.getCell(6).setCellStyle(style1);
        sheet.setColumnWidth(6, width2);
        rowNum = 2;
        row = sheet.createRow(rowNum);
        row.setHeight((short)600);
        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        row.createCell(3).setCellValue("Дата");
        row.getCell(3).setCellStyle(style1);
        row.createCell(4).setCellValue("Номер");
        row.getCell(4).setCellStyle(style1);
        row.createCell(5).setCellValue("Удержание");
        row.getCell(5).setCellStyle(style1);
        row.createCell(6).setCellValue("Возмещение");
        row.getCell(6).setCellStyle(style1);
        row.getCell(0).setCellStyle(style1);
        row.getCell(1).setCellStyle(style1);
        row.getCell(2).setCellStyle(style1);
        // заполняем лист данными
        for (Adds adds : addsList ) {
            if (adds.getAdd3exist()!=null){
            createSheetHeader(sheet, ++rowNum, adds);
            }
        }


        // записываем созданный в памяти Excel документ в файл
        File outputFile = new File("C:\\temp\\rovList.xls");
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientIP = request.getRemoteAddr();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Journal_reshenie_o_vziskanii.xls");

        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Журнал учета решений о взыскании сумм пенсии, излишне выплаченных пенсионеру / ADD7 " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}

