package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.domain.Adds;
import com.firstprog.pereplats.domain.Logs;
import com.firstprog.pereplats.domain.Pensioner;
import com.firstprog.pereplats.domain.User;
import com.firstprog.pereplats.model.Vpd;
import com.firstprog.pereplats.repos.AddsRepo;
import com.firstprog.pereplats.repos.LogsRepo;
import com.firstprog.pereplats.repos.PensRepo;
import com.firstprog.pereplats.repos.VpdRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.core.io.FileSystemResource;
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
public class Add1Controller {
    @Autowired
    private AddsRepo addsRepo;
    @Autowired
    private PensRepo pensRepo;
    @Autowired
    private VpdRepository vpdRepository;
    @Autowired
    private LogsRepo logsRepo;

    //формирование excel документа с решением об обнаружении ошибки

    @RequestMapping(value = "/getAdd1File.docx", method = RequestMethod.GET)
    @ResponseBody
    private FileSystemResource createAdd1(HttpServletRequest request, @AuthenticationPrincipal User user1, Adds adds, Integer id, User user, HttpServletResponse response) throws IOException, ParseException {
        String filepath = "./src/main/resources/doc_templates/add_1.docx";
        String outpath = "C:\\temp\\Test1.docx";
        List<Adds> addsList = addsRepo.findAddsByPensid(id); //ищем решение по ID пенсионера
        List<Pensioner> pensionerList = pensRepo.findPensionerById(id);
        String pensnomer = pensionerList.get(0).getPensnomer();//ищем пенсионера по ID
        String vpd = pensionerList.get(0).getVpd(); //получаем список ВПД
        List<Vpd> vpdList = vpdRepository.findByVpd(vpd); //достаем строку ВПД по ВПД пенсионера
        String vpdskl = vpdList.get(0).getVpdskl(); //получаем ВПД со склонением для печати
        String snils = pensionerList.get(0).getSnl();

        //ВНИМАНИЕ! данные пенсионера берутся из таблицы pensioner, а данные, относящиеся
        //к документу - из Adds.

        //Перевод даты из ГГГГ-ММ-ДД в ДД.ММ.ГГГГ

        String oldDateString = addsList.get(0).getAdd1reshdate();
        String oldDateString1 = pensionerList.get(0).getCalendar();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); //дата в исходном формате
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());  //дата в новом формате
        SimpleDateFormat oldDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = oldDateFormat.parse(oldDateString);
        String result = newDateFormat.format(date);
        Date date1 = oldDateFormat1.parse(oldDateString1);
        String result1 = newDateFormat1.format(date1);

        //ID из Int в String для печати

        String strid = String.valueOf(addsList.get(0).getPensid());

        //Заполнение шаблона данными заменой символов на данны из таблицы Adds, относящиеся
        //к решению

        XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath));

        for (XWPFParagraph p : doc.getParagraphs()) {
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text != null && text.contains("$")) {
                    text = text.replace("$1", result); //дата в новом формате
                    text = text.replace("$2", pensnomer); //ID в виде строки
                    text = text.replace("$3", addsList.get(0).getUpr()); //Управление
                    text = text.replace("$4", vpdskl); //ВПД в склонении
                    text = text.replace("$5", pensionerList.get(0).getVp()); //Вид пенсии
                    text = text.replace("$6", pensionerList.get(0).getFio()); //ФИО
                    text = text.replace("$7", pensionerList.get(0).getSnl()); //СНИЛС
                    text = text.replace("$8", pensionerList.get(0).getNvd()); //№ ВД
                    text = text.replace("$9", addsList.get(0).getAdd1opismistake()); //описание ошибки
                    text = text.replace("$0", result1); //новая дата1
                    text = text.replace("$s", addsList.get(0).getAdd1statja()); //статья
                    text = text.replace("$m", addsList.get(0).getAdd1mistake()); //ошибка
                    text = text.replace("$a", addsList.get(0).getAdd1viprazn()); //размер выплаты
                    text = text.replace("$$", addsList.get(0).getUprnach()); //Начальник управления
                    r.setText(text, 0);
                }
            }
        }

        //создание готового файла из шаблона

        File outputFile = new File(outpath);
        try (FileOutputStream out = new FileOutputStream(outpath)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String doc_nazv = "Reshenie_ob_oshibke_";
        String clientIP = request.getRemoteAddr();
        //замена кириллицы на латиницу для документа

        String file_fio = cyr2lat(pensionerList.get(0).getFio());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + doc_nazv + file_fio + ".doc\"");
        response.setContentType("application/msword");
        System.out.println("Файл успешно создан");
        Logs logs;
        String username = user1.getUsername();
        String description =  "Печать / Решение об обнаружении ошибки " + snils + " / ADD1 / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        // File file_del = new File(outpath);
        // if(file_del.delete()){
        //    System.out.println("Тестовый файл удален");
        //}else System.out.println("Тестовый файл не существует");
        return new FileSystemResource(outputFile);
    }

}
