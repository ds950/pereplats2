package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.Add4;
import com.firstprog.pereplats.domain.Logs;
import com.firstprog.pereplats.domain.Pensioner;
import com.firstprog.pereplats.domain.User;
import com.firstprog.pereplats.repos.Add4Repo;
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

import static com.firstprog.pereplats.domain.Translit.cyr2lat;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

@Controller
public class Add4Controller {
    HSSFCellStyle style2;
    @Autowired
    private Add4Repo add4Repo;
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private LogsRepo logsRepo;

    // заполнение строки (rowNum) определенного листа (sheet)
    // данными  из dataModel созданного в памяти Excel файла
    private void createSheetHeader(HSSFSheet sheet, int rowNum, Add4 add4) throws ParseException {

        //создание строки в документе

        Row row = sheet.createRow(rowNum);

        //вставка значения с месяцем выплаты в ячейку строки
        row.createCell(0).setCellValue(add4.getViphismonth());
        int width = (int) (6 * 1.14388) * 512; // размер ячейки
        //задаем размер ячейки
        sheet.setColumnWidth(0, width);

        //вставка значения с выплаченной суммой в ячейку
        row.createCell(1).setCellValue(add4.getVip_summ());
        width = (int) (6 * 1.14388) * 512;
        sheet.setColumnWidth(1, width);

        //вставка значения с начисленной суммой
        row.createCell(2).setCellValue(add4.getNach_summ());
        width = (int) (6 * 1.14388) * 512;
        sheet.setColumnWidth(2, width);

        //вставка значения с разницей между ВЫП и НАЧ суммами
        row.createCell(3).setCellValue(add4.getVip_razn());
        width = (int) (6 * 1.14388) * 512;
        sheet.setColumnWidth(3, width);

        //задаем стиль ячейкам
        row.getCell(0).setCellStyle(style2);
        row.getCell(1).setCellStyle(style2);
        row.getCell(2).setCellStyle(style2);
        row.getCell(3).setCellStyle(style2);
    }

    @RequestMapping(value = "/getAdd4File.xls", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getAdd4FileXls(HttpServletRequest request, @AuthenticationPrincipal User user, Integer id, HttpServletResponse response, String month1, String month2) throws ParseException {

        // создание самого excel файла в памяти
        // задание стиля
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setWrapText(true);

        style2 = workbook.createCellStyle();
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setWrapText(true);
        style2.setBorderTop(THIN);
        style2.setBorderBottom(THIN);
        style2.setBorderLeft(THIN);
        style2.setBorderRight(THIN);
        //style.setFillForegroundColor (HSSFColor.BLUE.index);
        //style.setFillPattern (FillPatternType.SOLID_FOREGROUND);
        // создание листа с названием "Просто лист"
        HSSFSheet sheet = workbook.createSheet("Рассчет");
        // заполняем лист данными
        List<Pensioner> pensionerList = pensRepo.findPensionerById(id); // ищем пенсионера по ID
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(id); //получаем список выплат
        String snils = pensionerList.get(0).getSnl();
        //сортированный по месяцам по возрастанию


        // счетчик для строк

        int rowNum = 0;

        //слияние ячеек

        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 7));

        Row row = sheet.createRow(rowNum);

        //заполняем заголовок документа

        rowNum = 0;
        row = sheet.createRow(rowNum);
        //вставляем район УПФР
        row.createCell(0).setCellValue("УПФР в " + add4List.get(0).getUpr() + " районе");
        row.getCell(0).setCellStyle(style1);
        rowNum = 2;
        row = sheet.createRow(rowNum);
        //номер выплатного дела
        row.createCell(0).setCellValue("выплатное дело № " + pensionerList.get(0).getNvd());
        row.getCell(0).setCellStyle(style1);
        rowNum = 3;
        row = sheet.createRow(rowNum);
        //ФИО
        row.createCell(0).setCellValue("ФИО: " + pensionerList.get(0).getFio());
        row.getCell(0).setCellStyle(style1);
        rowNum = 4;
        row = sheet.createRow(rowNum);
        //Адрес пенсионера
        row.createCell(0).setCellValue("проживающий(ая) по адресу: " + pensionerList.get(0).getAdress());
        row.getCell(0).setCellStyle(style1);
        rowNum = 5;
        row = sheet.createRow(rowNum);
        //Вид пенсии
        row.createCell(0).setCellValue("Вид пенсии: " + pensionerList.get(0).getVp());
        row.getCell(0).setCellStyle(style1);
        rowNum = 6;
        row = sheet.createRow(rowNum);
        //За период с ... по...
        row.createCell(0).setCellValue("История выплаты за период с " + month2 + " по " + month1);
        row.getCell(0).setCellStyle(style1);
        rowNum = 7;
        row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Месяц \r\nвыплаты");
        row.getCell(0).setCellStyle(style2);
        row.createCell(1).setCellValue("Сумма \r\nвыплаченная");
        row.getCell(1).setCellStyle(style2);
        row.createCell(2).setCellValue("Сумма \r\nначисленная");
        row.getCell(2).setCellStyle(style2);
        row.createCell(3).setCellValue("Разница");
        row.getCell(3).setCellStyle(style2);
        // заполняем лист данными
        double sumVip = 0.0;
        double sumNach = 0.0;
        double sumRazn = 0.0;; //последний месяц
        Date monthFrom;//первый месяц
        Date monthTo;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            monthFrom = sdf.parse(month2); //первая вылата
            monthTo = sdf.parse(month1); //последняя выплата
        } catch (Exception E) {
            monthFrom = null;
            monthTo = null;
        }
        for (Add4 add4 : add4List) {
            Date add4Date;
            try {
                String ddd = add4.getViphismonth();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                add4Date = sdf.parse(ddd);
            } catch (Exception E) {
                continue;
            }
            //если месяца не пустые
            if (monthFrom != null && monthTo != null &&
                    //после первой даты и до второй даты...
                    ((add4Date.after(monthFrom) && add4Date.before(monthTo)) ||//
                            add4Date.equals(monthFrom) || add4Date.equals(monthTo))) {//
                createSheetHeader(sheet, ++rowNum, add4);
                //сумма выплаченных сумм
                sumVip += Double.parseDouble(add4.getVip_summ());
                //сумма начисленных сумм
                sumNach += Double.parseDouble(add4.getNach_summ());
                //сумма итоговая
                sumRazn += Double.parseDouble(add4.getVip_razn());
            }
        }
        row = sheet.createRow(++rowNum);
        row.createCell(0).setCellValue("Итого");
        row.getCell(0).setCellStyle(style2);
        row.createCell(1).setCellValue("");
        row.getCell(1).setCellStyle(style2);
        row.createCell(2).setCellValue("");
        row.getCell(2).setCellStyle(style2);
        //итоговая сумма
        row.createCell(3).setCellValue(sumRazn);
        row.getCell(3).setCellStyle(style2);
        rowNum = rowNum + 1;
        row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Итого \r\nпереплаты");
        row.createCell(3).setCellValue(sumRazn);

        // записываем созданный в памяти Excel документ в файл
        File outputFile = new File("C:\\temp\\Add4.xls");
        try (FileOutputStream out = new FileOutputStream(outputFile)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientIP = request.getRemoteAddr();
        //кириллица ФИО в латиницу
        String file_fio = cyr2lat(pensionerList.get(0).getFio());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Rass_summ_per_" + file_fio + ".xls");

        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Расчет излишне выплаченных сумм пенсии " + snils + "/ ADD4 /" +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }

}

