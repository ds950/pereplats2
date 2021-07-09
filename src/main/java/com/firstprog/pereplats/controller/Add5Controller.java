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
import java.util.Date;
import java.util.List;

import static com.firstprog.pereplats.domain.Translit.cyr2lat;

@Controller
public class Add5Controller {
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private LogsRepo logsRepo;

    @RequestMapping(value = "/getAdd5File.docx", method = RequestMethod.GET)
    @ResponseBody
    FileSystemResource createAdd5(HttpServletRequest request, @AuthenticationPrincipal User user, Integer id, Adds adds, HttpServletResponse response) throws IOException {
        String filepath = "./src/main/resources/doc_templates/add_5.docx";
        String outpath = "C:\\Test5.docx";
        List<Adds> addsList = addsRepo.findAddsByPensid(id); //поиск извещения по id пенсионера
        List<Pensioner> pensionerList = pensRepo.findPensionerById(id); //поиск пенсионера по его id
        String snils = pensionerList.get(0).getSnl();


        //ВНИМАНИЕ! данные пенсионера берутся из таблицы pensioner, а данные, относящиеся
        //к документу - из Adds

        XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath));
        for (XWPFParagraph p : doc.getParagraphs()) {
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text != null && text.contains("$")) {
                    text = text.replace("$1", pensionerList.get(0).getFio()); //ФИО
                    text = text.replace("$2", pensionerList.get(0).getAdress()); //Адрес
                    text = text.replace("$3", addsList.get(0).getUpr()); //район УПФР
                    text = text.replace("$4", addsList.get(0).getUpradress()); //адрес УПФР
                    text = text.replace("$5", addsList.get(0).getUprnach()); //начальник УПФР
                    text = text.replace("$6", addsList.get(0).getUprisp()); //исполнитель
                    text = text.replace("$7", addsList.get(0).getTel()); //телефон
                    text = text.replace("$9", addsList.get(0).getAdd2summrub());
                    r.setText(text, 0);
                }

            }

        }

        File outputFile = new File(outpath);
        try (FileOutputStream out = new FileOutputStream(outpath)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientIP = request.getRemoteAddr();
        //фио из кириллицы в латиницу
        String file_fio = cyr2lat(pensionerList.get(0).getFio());
        response.setContentType("application/vnd.ms-word");
        response.setHeader("Content-Disposition", "attachment; filename=Izveschenie_" + file_fio + "_o_summe_pereplati.docx");

        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Извещение пенсионеру о сумме образовавшейся переплаты " + snils + "/ ADD5 / " +clientIP ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}