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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.firstprog.pereplats.domain.Translit.cyr2lat;

@Controller
public class Add9Controller {
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private LogsRepo logsRepo;

    //Создание протокола о выявлении ошибки

    @RequestMapping(value = "/getAdd9File.docx", method = RequestMethod.GET)
    @ResponseBody
    FileSystemResource createAdd2(@AuthenticationPrincipal User user, Integer id, Adds adds, HttpServletResponse response) throws IOException, ParseException {
        String filepath = "./src/main/resources/doc_templates/add_9.docx";
        String outpath = "C:\\Test9.docx";

        List<Adds> addsList = addsRepo.findAddsByPensid(id); //получаем протокол по ID
        List<Pensioner> pensionerList = pensRepo.findPensionerById(id);
        String pensnomer = pensionerList.get(0).getPensnomer();//получаем пенсионера по ID
        String snils = pensionerList.get(0).getSnl();

        //ВНИМАНИЕ! данные пенсионера берутся из таблицы pensioner, а данные, относящиеся
        //к документу - из Adds.

        //ID в String для печати

        String protid = String.valueOf(addsList.get(0).getPensid());

        XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath));
        for (XWPFParagraph p : doc.getParagraphs()) {
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text != null && text.contains("$")) {
                    text = text.replace("$1", addsList.get(0).getAdd9raspnadp());//распорядительная надпись
                    text = text.replace("$2", addsList.get(0).getAdd9rukdolzh());//должность руководителя
                    text = text.replace("$3", addsList.get(0).getAdd9rukfio());//ФИО руководителя
                    text = text.replace("$4", addsList.get(0).getAdd9protdate());//дата в формате ДД.ММ.ГГГГ
                    text = text.replace("$5", pensnomer);//номер протокола
                    text = text.replace("$6", addsList.get(0).getUprfull());//полное наименование УПФР
                    text = text.replace("$7", pensionerList.get(0).getFio());//ФИО
                    text = text.replace("$8", pensionerList.get(0).getSnl());//СНИЛС
                    text = text.replace("$9", pensionerList.get(0).getNvd());//№ ВД
                    text = text.replace("$0", addsList.get(0).getAdd9vv());//Вид выплаты
                    text = text.replace("$a", addsList.get(0).getAdd9date1());//дата в формате ДД.ММ.ГГГГ
                    text = text.replace("$b", addsList.get(0).getAdd9date2());//дата в формате ДД.ММ.ГГГГ
                    text = text.replace("$$", addsList.get(0).getAdd9summrub());//Сумма рублей
                    text = text.replace("$e", addsList.get(0).getAdd9prich()); //причина образования ИВСП
                    text = text.replace("$f", addsList.get(0).getAdd9provdolzh());//должность проверяющего
                    text = text.replace("$g", addsList.get(0).getAdd9provfio());//ФИО проверяющего
                    text = text.replace("$h", addsList.get(0).getAdd9proizvdolzh());//должность произв.расчет
                    text = text.replace("$i", addsList.get(0).getAdd9proizvfio());//ФИО произв.расчет
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

        //кириллица в латиницу для печати ФИО

        String file_fio = cyr2lat(pensionerList.get(0).getFio());
        response.setContentType("application/vnd.ms-word");
        response.setHeader("Content-Disposition", "attachment; filename=Protokol_" + file_fio + ".docx");

        Logs logs;
        String username = user.getUsername();
        String description =  "Печать / Протокол о выявлении ИВСП " + snils + "/ ADD9" ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        System.out.println("Excel файл успешно создан!");
        return new FileSystemResource(outputFile);
    }
}