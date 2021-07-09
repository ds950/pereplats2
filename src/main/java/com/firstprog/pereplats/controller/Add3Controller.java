package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.Adds;
import com.firstprog.pereplats.domain.Logs;
import com.firstprog.pereplats.domain.Pensioner;
import com.firstprog.pereplats.domain.User;
import com.firstprog.pereplats.repos.AddsRepo;
import com.firstprog.pereplats.repos.LogsRepo;
import com.firstprog.pereplats.repos.PensRepo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.firstprog.pereplats.domain.Translit.cyr2lat;

@Controller
public class Add3Controller {
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private LogsRepo logsRepo;

    @RequestMapping(value = "/getAdd3File.docx", method = RequestMethod.GET)
    @ResponseBody
    FileSystemResource createAdd3(HttpServletRequest request, @AuthenticationPrincipal User user, Integer id, HttpServletResponse response) throws IOException, ParseException {
        List<Adds> addsList = addsRepo.findAddsByPensid(id);//получаем решение по ID пенсионера
        List<Pensioner> pensionerList = pensRepo.findPensionerById(id);
        String pensnomer = pensionerList.get(0).getPensnomer();//получаем пенсионера по ID
        String snils = pensionerList.get(0).getSnl();

        //ВНИМАНИЕ! данные пенсионера берутся из таблицы pensioner, а данные, относящиеся
        //к документу - из Adds.

        String filepath;

        //условие выбора типа шаблона

        //если блок 2 пуст и размер в процентах = 0, то шаблон 3_1
        //если блок 1 пуст и размер в процентах = 0, то шаблон 3_2
        //если блок 2 пуст и размер в твердой сумме = 0, то щаблон 3_1_2
        //если блок 1 пуст и размер в твердой сумме = 0, то шаблон 3_2_2

        if (addsList.get(0).getBlock2fio().equals("") && addsList.get(0).getBlock3razmer().isEmpty()) {
            if (addsList.get(0).getBlock3date().equals("")){
                filepath = "./src/main/resources/doc_templates/add_3_1_3.docx";
            } else {
            filepath = "./src/main/resources/doc_templates/add_3_1.docx";
            }
        } else if (addsList.get(0).getBlock1fio().equals("") && addsList.get(0).getBlock3razmer().isEmpty()) {
            filepath = "./src/main/resources/doc_templates/add_3_2.docx";
        } else if (addsList.get(0).getBlock2fio().equals("") && addsList.get(0).getBlock3razmertverd().isEmpty()) {
            if (addsList.get(0).getBlock3date().isEmpty()) {
                filepath = "./src/main/resources/doc_templates/add_3_1_3.docx";
            } else {
            filepath = "./src/main/resources/doc_templates/add_3_1_2.docx"; }
        } else {
            filepath = "./src/main/resources/doc_templates/add_3_2_2.docx";
        }

        String outpath = "C:\\Test3.docx";

        String result = null;
        String result1 = null;
        String result2 = null;
        String razmer = null;
        String razmert = null;
        if (addsList.get(0).getBlock3razmer()==null){
            razmer = "";
        } else {
            razmer = addsList.get(0).getBlock3razmer();
        }
        if (addsList.get(0).getBlock3razmertverd()==null){
            razmert = "";
        } else {
            razmert = addsList.get(0).getBlock3razmertverd();
        }

        //замена старой даты на новую

        String oldDateString = addsList.get(0).getAdd3date();
        String oldDateString1 = addsList.get(0).getBlock1date();
        String oldDateString2 = addsList.get(0).getBlock3date();


        //если блок 1 пуст, то до дата в этом блоке не меняется

        if (oldDateString1.equals("")) {
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            SimpleDateFormat oldDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat2 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date date = oldDateFormat.parse(oldDateString);
            result = newDateFormat.format(date);
            Date date2 = oldDateFormat2.parse(oldDateString2);
            result2 = newDateFormat2.format(date2);
            result1 = "";
        } else if (oldDateString2.equals("")){
            //если
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            SimpleDateFormat oldDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = oldDateFormat.parse(oldDateString);
            result = newDateFormat.format(date);
            Date date1 = oldDateFormat1.parse(oldDateString1);
            result1 = newDateFormat1.format(date1);
            result2 = "";
            razmer = "";
            razmert = "";

        } else {
            //меняются все даты
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            SimpleDateFormat oldDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            SimpleDateFormat oldDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newDateFormat2 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            Date date = oldDateFormat.parse(oldDateString);
            result = newDateFormat.format(date);
            Date date1 = oldDateFormat1.parse(oldDateString1);
            result1 = newDateFormat1.format(date1);
            Date date2 = oldDateFormat2.parse(oldDateString2);
            result2 = newDateFormat2.format(date2);
        }


        //ID в строку для печати

        String strid = String.valueOf(addsList.get(0).getPensid());

        XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath));
        for (XWPFParagraph p : doc.getParagraphs()) {
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                String a = "$";
                if (text != null && text.contains(a)) {
                    text = text.replace("$1", result); // дата создания документа
                    text = text.replace("$2", pensnomer); //номер документа
                    text = text.replace("$3", addsList.get(0).getUpr()); //район УПФР
                    text = text.replace("$4", addsList.get(0).getBlock1fio()); //ФИО блока 1
                    text = text.replace("$5", addsList.get(0).getBlock1num()); //номер заявления блока 1
                    text = text.replace("$6", result1); //дата приема заявления
                    text = text.replace("$7", addsList.get(0).getBlock1snl()); //СНИЛС
                    text = text.replace("$8", addsList.get(0).getBlock1vd()); //№ выплатного дела
                    text = text.replace("$9", addsList.get(0).getBlock2fio()); //ФИО блок 2
                    text = text.replace("$0", addsList.get(0).getBlock2snl()); //СНИЛС
                    text = text.replace("$a", addsList.get(0).getBlock2vd()); //№ выплатного дела
                    text = text.replace("$b", pensionerList.get(0).getVp()); //вид пенсии
                    text = text.replace("$c", addsList.get(0).getKlass()); //статья
                    text = text.replace("$d", result2); //производить удержание С...
                    text = text.replace("$e", razmer); //размер в процентах
                    text = text.replace("$f", razmert); //твердая сумма
                    text = text.replace("$g", addsList.get(0).getUprnach()); //начальник управления
                    r.setText(text, 0);
                }

            }

        }

        //создание документа из шаблона

        File outputFile = new File(outpath);
        try (FileOutputStream out = new FileOutputStream(outpath)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ФИО из кириллицы в латиницу
        String clientIP = request.getRemoteAddr();
        String file_fio = cyr2lat(pensionerList.get(0).getFio());
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition", "attachment; filename=Reshenie_o_vziskanii_"+file_fio+".docx");

        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Решение о взыскании сумм пенсии " + snils + "/ ADD3 /" +clientIP ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}