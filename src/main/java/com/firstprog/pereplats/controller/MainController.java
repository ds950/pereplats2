package com.firstprog.pereplats.controller;

import com.firstprog.pereplats.dao.RosDao;
import com.firstprog.pereplats.domain.*;
import com.firstprog.pereplats.model.Klass;
import com.firstprog.pereplats.model.PtkPensioner;
import com.firstprog.pereplats.model.Vp;
import com.firstprog.pereplats.model.Vpd;
import com.firstprog.pereplats.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final AdminparamRepo adminparamRepo;
    private final MessageRepo messageRepo;
    private final VpdRepository vpdRepository;
    private final VpRepository vpRepository;
    private final PensRepo pensRepo;
    private final Add4Repo add4Repo;
    private final UprRepository uprRepository;
    private final KlassRepo klassRepo;
    private final DolzhnostRepo dolzhnostRepo;
    private final UserRepo userRepo;
    private final RosDao rosDao;
    private final AddsRepo addsRepo;
    private final LogsRepo logsRepo;
    public String FIO;
    //@Autowired
    //private PtkRepository ptkRepository;
    public String ADRESS;
    public String NVD;
    public String VP;
    public String SNILS;

    @Autowired
    public MainController(AdminparamRepo adminparamRepo, MessageRepo messageRepo, VpdRepository vpdRepository, VpRepository vpRepository, RosDao rosDao, PensRepo pensRepo, Add4Repo add4Repo, KlassRepo klassRepo, UserRepo userRepo, UprRepository uprRepository, DolzhnostRepo dolzhnostRepo, AddsRepo addsRepo, LogsRepo logsRepo) {
        this.adminparamRepo = adminparamRepo;
        this.messageRepo = messageRepo;
        this.vpdRepository = vpdRepository;
        this.vpRepository = vpRepository;
        this.rosDao = rosDao;
        this.pensRepo = pensRepo;
        this.add4Repo = add4Repo;
        this.klassRepo = klassRepo;
        this.userRepo = userRepo;
        this.uprRepository = uprRepository;
        this.dolzhnostRepo = dolzhnostRepo;
        this.addsRepo = addsRepo;
        this.logsRepo = logsRepo;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/login")
    public String login(User user, Map<String, Object> model, HttpSession session) {

        return "login";
    }

    @GetMapping("/adminpage")
    public String adminpage(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model){
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Администрирование / Администратор вошел на страницу администрирования / " + username + "/ adminpage / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        model.put("FIO", user.getUser_fio());
        model.put("UNAME", user.getUsername());
        return "adminpage";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam String username, HttpSession httpSession) {
        String loggedUser = username;
        httpSession.setAttribute("user", loggedUser);
        return "login";
    }

    @GetMapping("/pensvariants")
    public String pensvariants(String user, Map<String, Object> model, HttpSession httpSession) {
        return "pensvariants";
    }

    @GetMapping("/emptydocs")
    public String emptydocs(String user, Map<String, Object> model, HttpSession httpSession) {
        return "emptydocs";
    }

    @GetMapping("/attempts")
    public String attempts(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model) {
        List <User> userList = userRepo.findByActive(3);
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Администрирование / Вход на страницу обнуления попыток / " + username + " / attempts / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        model.put("USER", userList);
        model.put("UNAME", user.getUsername());
        model.put("FIO", user.getUser_fio());
        return "attempts";
    }

    @GetMapping("/tries")
    public String tries(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model) {
        Adminparam adminparam = adminparamRepo.findById(1L);
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Администрирование / Вход на страницу изменения числа попыток / " + username + "/ tries / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        model.put("TRIES", adminparam);
        model.put("UNAME", user.getUsername());
        model.put("FIO", user.getUser_fio());
        return "tries";
    }

    @GetMapping("/emptyedit")
    public String emptyedit(String user, Map<String, Object> model, HttpSession httpSession) {
        return "emptyedit";
    }

    @GetMapping(value = "/pensselect")
    public String pensselect(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        String ucode = user.getUprcode();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Администрирование / Вход на страницу выбора пенсионера / " + username + "/ pensselect / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        Iterable<Pensioner> pensionerList = pensRepo.findAllByUprcodeOrderByIdAsc(ucode);
        model.put("pensioner", pensionerList);
        return "pensselect";
    }

    @GetMapping(value = "/logs")
    public String logs(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        List<Logs> logsList = logsRepo.findAllByOrderByDateAsc();
        model.put("FIO", user.getUser_fio());
        model.put("UNAME", user.getUsername());
        model.put("LOGS", logsList);

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Администрирование / Вход на страницу с логами / " + username + "/ logs / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + user.getUsername() + " / " + description);
        LogToFile.Logi(loggs);

        return "logs";
    }

    @GetMapping(value = "/deletepage")
    public String deletepage(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session) {
        List<Pensioner> pensionerList = pensRepo.findAll();
        model.put("FIO", user.getUser_fio());
        model.put("UNAME", user.getUsername());
        model.put("PENSIONER", pensionerList);
        return "deletepage";
    }

    @PostMapping(value = "/pensselect")
    public RedirectView pensselect(HttpServletRequest request, @AuthenticationPrincipal User user, Integer snl, Map<String, Object> model, HttpSession session) {
        session.setAttribute("id", snl);
        Pensioner pensioner = pensRepo.findById(snl);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Документы / Выбор пенсионера для работы с документами / " + snils + "/ pensselect / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        return new RedirectView("doccreate");
    }

    @GetMapping(value = "/doccreate")
    public String doccreate(@AuthenticationPrincipal User user, Map<String, Object> model, HttpServletRequest request, HttpSession session) {
        session = request.getSession();
        Integer SNILS = (Integer) session.getAttribute("id");
        List<Pensioner> pensionerList = pensRepo.findPensionerById(SNILS);
        String snils = pensionerList.get(0).getSnl();
        String FIO = pensionerList.get(0).getFio();
        String ADRESS = pensionerList.get(0).getAdress();
        String NVD = pensionerList.get(0).getNvd();
        String VP = pensionerList.get(0).getVp();
        String VPD = pensionerList.get(0).getVpd();
        String DATEMISTAKE = pensionerList.get(0).getCalendar();
        session.setAttribute("fio", FIO);
        session.setAttribute("adress", ADRESS);
        session.setAttribute("nvd", NVD);
        session.setAttribute("vp", VP);
        session.setAttribute("vpd", VPD);
        session.setAttribute("datemistake", DATEMISTAKE);
        session.setAttribute("snils", SNILS);
        model.put("FIO", FIO);

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Документы / Пенсионер выбран. Выбор документов для создания / " + snils + "/ doccreate / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return "doccreate";
    }

    @GetMapping("/add2create")
    public String add2create(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) throws ParseException {

        session = request.getSession();
        Integer PENSID = (Integer) session.getAttribute("id");
        if (PENSID == null) {
            return "pensvariants";
        } else {

        List<Adds> addsList = addsRepo.findAddsByPensid(PENSID); //ADDS LIST
        Integer ID = addsList.get(0).getPensid();

        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID); //PENSIONER LIST
        String SNILS = pensionerList.get(0).getSnl();

        List <Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(PENSID); //ADD4 LIST
        Double RESULT = Math.abs(add4List.get(0).getResult_final());
            model.put("add4", add4List);

            List <Add4> add4List1 = add4Repo.findByPensidOrderByViphismonthDesc(PENSID); //ADD4 LIST
            model.put("add4_1", add4List1);

        Iterable<Upr> upr = uprRepository.findAll(); //UPRAVLENIJA
        model.put("upr", upr);


       // Iterable<Dolzhnost> dolzhnostList = dolzhnostRepo.findAll(); //DOLZHNOSTI
       // model.put("dolzhnost", dolzhnostList);

        String upravlenie = user.getUpravlenie(); //UPRAVLENIE by USER
        model.put("UPRAVLENIE", upravlenie);

        List<Upr> upr1 = uprRepository.findByUpr(upravlenie); //UPR LIST
        String isp = user.getUser_fio();
        String tel = user.getUser_tel();
        model.put("upr1", upr1);

        String uprfull = upr1.get(0).getUpr_full(); //UPR DATA
        String upradress = upr1.get(0).getUpr_adress();
        String uprnach = upr1.get(0).getUpr_nach();

            String ADD2FIO = pensionerList.get(0).getFio(); //PENSIONER DATA + ADD1 DATA
            String ADD2SNILS = pensionerList.get(0).getSnl();
            String ADD2ADRESS = pensionerList.get(0).getAdress();
            String ADD2NVD = pensionerList.get(0).getNvd();
            String ADD2IV = pensionerList.get(0).getVp();
            String REASON = addsList.get(0).getAdd1opismistake();
            String RESHDATE = addsList.get(0).getAdd1reshdate();

            model.put("ADD2ID", PENSID); //MODELS
            model.put("ADD2FIO", ADD2FIO);
            model.put("ADD2SNILS", ADD2SNILS);
            model.put("ADD2ADRESS", ADD2ADRESS);
            model.put("ADD2NVD", ADD2NVD);
            model.put("ISP", isp);
            model.put("TEL", tel);
            model.put("REASON", REASON);
            model.put("RESHDATE", RESHDATE);
            model.put("UPRFULL", uprfull);
            model.put("UPRADRESS", upradress);
            model.put("UPRNACH", uprnach);
            model.put("PENSID", ID);
            model.put("RESULT", RESULT);
            model.put("ADD2IV", ADD2IV);
            model.put("PFIO", ADD2FIO);

            String clientIP = request.getRemoteAddr();
            Logs logs;
            String username = user.getUsername();
            String description =  "Создание документов / Пользователь приступил к формированию Протокола / " + ADD2SNILS + "/ add2create / " +clientIP;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "add2create";
        }
    }

    @GetMapping("/add9create")
    public String add9create(@AuthenticationPrincipal User user, Add4 add4, Map<String, Object> model, HttpSession session, HttpServletRequest request) throws ParseException {

        session = request.getSession();
        Integer PENSID = (Integer) session.getAttribute("id");
        if (PENSID == null) {
            return "pensvariants";
        } else {

            List<Adds> addsList = addsRepo.findAddsByPensid(PENSID); //ADDS LIST
            Integer ID = addsList.get(0).getPensid();

            List<Pensioner> pensionerList = pensRepo.findPensionerById(ID); //PENSIONER LIST

            List <Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(PENSID); //ADD4 LIST
            List <Add4> add4ListDesc = add4Repo.findByPensidOrderByViphismonthDesc(PENSID); //ADD4 LIST
            double RESULT = Math.abs(add4List.get(0).getResult_final());
            model.put("add4", add4List);
            model.put("add4desc", add4ListDesc);
            Iterable<Upr> upr = uprRepository.findAll(); //UPRAVLENIJA
            model.put("upr", upr);

            // Iterable<Dolzhnost> dolzhnostList = dolzhnostRepo.findAll(); //DOLZHNOSTI
            // model.put("dolzhnost", dolzhnostList);

            String upravlenie = user.getUpravlenie(); //UPRAVLENIE by USER
            model.put("UPRAVLENIE", upravlenie);

            List<Upr> upr1 = uprRepository.findByUpr(upravlenie); //UPR LIST
            String isp = user.getUser_fio();
            String tel = user.getUser_tel();
            model.put("upr1", upr1);

            String uprfull = upr1.get(0).getUpr_full(); //UPR DATA
            String upradress = upr1.get(0).getUpr_adress();
            String uprnach = upr1.get(0).getUpr_nach();

            String ADD9FIO = pensionerList.get(0).getFio(); //PENSIONER DATA + ADD1 DATA
            String ADD9SNILS = pensionerList.get(0).getSnl();
            String ADD9ADRESS = pensionerList.get(0).getAdress();
            String ADD9NVD = pensionerList.get(0).getNvd();
            String ADD9IV = pensionerList.get(0).getVp();
            String RESHDATE = addsList.get(0).getAdd3date();

            model.put("ADD9ID", ID); //MODELS
            model.put("ADD9FIO", ADD9FIO);
            model.put("ADD9SNILS", ADD9SNILS);
            model.put("ADD9ADRESS", ADD9ADRESS);
            model.put("ADD9NVD", ADD9NVD);
            model.put("ISP", isp);
            model.put("TEL", tel);
            model.put("RESHDATE", RESHDATE);
            model.put("UPRFULL", uprfull);
            model.put("UPRADRESS", upradress);
            model.put("UPRNACH", uprnach);
            model.put("PENSID", ID);
            model.put("RESULT", RESULT);
            model.put("ADD9IV", ADD9IV);
            model.put("PFIO", ADD9FIO);

            String clientIP = request.getRemoteAddr();
            Logs logs;
            String username = user.getUsername();
            String description =  "Создание документов / Пользователь приступил к формированию Протокола / " + ADD9SNILS + "/ add9create / " +clientIP;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "add9create";
        }
    }

    @GetMapping("/editpage")
    public String editpage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        String ucode = user.getUprcode();
        Iterable<Pensioner> pensionerList = pensRepo.findAllByUprcodeOrderByIdAsc(ucode);
        model.put("pensioner", pensionerList);
        return "editpage";
    }

    @PostMapping("/editpage")
    public RedirectView editpage(HttpServletRequest request, @AuthenticationPrincipal User user, Integer id, Map<String, Object> model, HttpSession session) {
        session.setAttribute("id", id);
        Pensioner pensioner = pensRepo.findById(id);
        String snils = pensioner.getSnl();

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Выбор пенсионера для редактирования данных / " + snils + " / editpage / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @GetMapping("/addprint")
    public String addprint(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session) {
        String ucode = user.getUprcode();
        Iterable<Pensioner> pensionerList = pensRepo.findAllByUprcodeOrderByIdAsc(ucode);
            model.put("pensioner", pensionerList);
            return "addprint";
        }

    @PostMapping("/addprint")
    public RedirectView addprint(HttpServletRequest request, @AuthenticationPrincipal User user, Integer id, Map<String, Object> model, HttpSession session) {
        session.setAttribute("id", id);
        Pensioner pensioner = pensRepo.findById(id);
        String snils = pensioner.getSnl();

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Печать документов / Выбор пенсионера для печати / " + snils + " / addprint / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        return new RedirectView("printvariants");
    }

    @GetMapping("/afterprintvariants")
    public String afterprintvariants(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) throws ParseException {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        model.put("ID", ID);
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String fio = pensionerList.get(0).getFio();
        String snl = pensionerList.get(0).getSnl();
        model.put("PFIO", fio);
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        if (addsList.get(0).getAdd1exist() == null || addsList.get(0).getAdd5exist() == null) {
            String pfriw = "false";
            model.put("PFRIW", pfriw);
        } else {
            model.put("PFRIW", "true");
        }
        List<Add4> add4after = add4Repo.findByPensidOrderByViphismonthAsc(ID);
        model.put("MA", add4after.get(0).getViphismonth());
        List<Add4> add4before = add4Repo.findByPensidOrderByViphismonthDesc(ID);
        model.put("MB", add4before.get(0).getViphismonth());

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Пользователь приступил печати документов / " + snl + "/ afterprintvariants / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return "afterprintvariants";
    }

    @GetMapping("/printvariants")
    public String printvariants(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) throws ParseException {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        model.put("ID", ID);
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        if (addsList.isEmpty()) {
            return "emptydocs";
        } else {
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String fio = pensionerList.get(0).getFio();
        String snl = pensionerList.get(0).getSnl();
        model.put("PFIO", fio);
            if (addsList.get(0).getAdd1exist() == null || addsList.get(0).getAdd5exist() == null) {
                String pfriw = "false";
                model.put("PFRIW", pfriw);
            } else {
                model.put("PFRIW", "true");
            }
            List<Add4> add4after = add4Repo.findByPensidOrderByViphismonthAsc(ID);
            model.put("MA", add4after.get(0).getViphismonth());
            List<Add4> add4before = add4Repo.findByPensidOrderByViphismonthDesc(ID);
            model.put("MB", add4before.get(0).getViphismonth());

            String clientIP = request.getRemoteAddr();
            Logs logs;
            String username = user.getUsername();
            String description =  "Печать документов / Пользователь приступил к печати документов / " + snl + "/ printvariants / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "printvariants";
        }
    }

    @GetMapping("/editvariants")
    public String editvariants(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        model.put("ID", ID);
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        if (addsList.isEmpty()) {
            return "emptyedit";
        } else {
            List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
            model.put("EDFIO", pensionerList.get(0).getFio());
            String snl = pensionerList.get(0).getSnl();


            if (addsList.get(0).getAdd1exist() == null || addsList.get(0).getAdd2exist() == null
                    || addsList.get(0).getAdd5exist() == null) {
                model.put("WHO IS WRONG", "P");
            } else {
                model.put("WHO IS WRONG", "U");
            }

            String clientIP = request.getRemoteAddr();
            Logs logs;
            String username = user.getUsername();
            String description =  "Редактирование документов / Пользователь приступил к редактированию документов пенсионера / " + snl + "/ editvariants / " +clientIP;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "editvariants";
        }
    }

    @GetMapping("/pensedit")
    public String pensedit(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        Integer pensid = pensionerList.get(0).getId();
        String pensfio = pensionerList.get(0).getFio();
        String penssnl = pensionerList.get(0).getSnl();
        String pensnvd = pensionerList.get(0).getNvd();
        String pensvp = pensionerList.get(0).getVp();
        String pensvpd = pensionerList.get(0).getVpd();
        String pensadress = pensionerList.get(0).getAdress();
        String penscalendar = pensionerList.get(0).getCalendar();

        model.put("PENSID", pensid);
        model.put("PENSFIO", pensfio);
        model.put("PENSSNL", penssnl);
        model.put("PENSNVD", pensnvd);
        model.put("PENSVP", pensvp);
        model.put("PENSVPD", pensvpd);
        model.put("PENSADRESS", pensadress);
        model.put("PENSCALENDAR", penscalendar);

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование документов / Пользователь приступил к редактированию данных пенсионера / " + penssnl + "/ pensedit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return "pensedit";
    }

    @GetMapping("/add2edit")
    public String add2edit(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        List <Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String snl = pensionerList.get(0).getSnl();
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(ID);
        model.put("add4", add4List);
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        String exist = addsList.get(0).getAdd2exist();
        if (exist == null) {
            String valid = "false";
            model.put("VALID", valid);
            return "editvariants";
        } else {
            model.put("VALID", "true");
            Integer pensid = addsList.get(0).getPensid();
            String add2prich = addsList.get(0).getAdd2prich();
            String add2proizvdolzh = addsList.get(0).getAdd2proizvdolzh();
            String add2proizvfio = addsList.get(0).getAdd2proizvfio();
            String add2provdolzh = addsList.get(0).getAdd2provdolzh();
            String add2provfio = addsList.get(0).getAdd2provfio();
            String add2vv = addsList.get(0).getAdd2vv();
            String add2mistakeauthor = addsList.get(0).getAdd2mistakeauthor();
            String add2date1 = addsList.get(0).getAdd2date1();
            String add2date2 = addsList.get(0).getAdd2date2();
            String add2protdate = addsList.get(0).getAdd2protdate();
            String add2raspnadp = addsList.get(0).getAdd2raspnadp();
            String add2rukdolzh = addsList.get(0).getAdd2rukdolzh();
            String add2rukfio =  addsList.get(0).getAdd2rukfio();
            String add2summrub = addsList.get(0).getAdd2summrub();

            model.put("ID", pensid);
            model.put("ADD2PRICH", add2prich);
            model.put("ADD2PZD", add2proizvdolzh);
            model.put("ADD2PZF", add2proizvfio);
            model.put("ADD2PD", add2provdolzh);
            model.put("ADD2PF", add2provfio);
            model.put("ADD2VV", add2vv);
            model.put("ADD2MA", add2mistakeauthor);
            model.put("ADD2DATE1", add2date1);
            model.put("ADD2DATE2", add2date2);
            model.put("ADD2PROTDATE", add2protdate);
            model.put("ADD2RASPNADP", add2raspnadp);
            model.put("ADD2RUKDOLZH", add2rukdolzh);
            model.put("ADD2RUKFIO", add2rukfio);
            model.put("ADD2SUMMRUB", add2summrub);
            model.put("PFIO", pensionerList.get(0).getFio());
        }

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование документов / Пользователь приступил к редактированию документов пенсионера / " + snl + "/ add2edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        return ("add2edit");
    }

    @GetMapping("/add9edit")
    public String add9edit(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        List <Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String snl = pensionerList.get(0).getSnl();
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(ID);
        model.put("add4", add4List);
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        String exist = addsList.get(0).getAdd9exist();
        if (exist == null) {
            String valid = "false";
            model.put("VALID", valid);
            return "editvariants";
        } else {
            model.put("VALID", "true");
            Integer pensid = addsList.get(0).getPensid();
            String add9prich = addsList.get(0).getAdd9prich();
            String add9proizvdolzh = addsList.get(0).getAdd9proizvdolzh();
            String add9proizvfio = addsList.get(0).getAdd9proizvfio();
            String add9provdolzh = addsList.get(0).getAdd9provdolzh();
            String add9provfio = addsList.get(0).getAdd9provfio();
            String add9vv = addsList.get(0).getAdd9vv();
            String add9mistakeauthor = addsList.get(0).getAdd9mistakeauthor();
            String add9date1 = addsList.get(0).getAdd9date1();
            String add9date2 = addsList.get(0).getAdd9date2();
            String add9protdate = addsList.get(0).getAdd9protdate();
            String add9raspnadp = addsList.get(0).getAdd9raspnadp();
            String add9rukdolzh = addsList.get(0).getAdd9rukdolzh();
            String add9rukfio =  addsList.get(0).getAdd9rukfio();
            String add9summrub = addsList.get(0).getAdd9summrub();

            model.put("ID", pensid);
            model.put("ADD9PRICH", add9prich);
            model.put("ADD9PZD", add9proizvdolzh);
            model.put("ADD9PZF", add9proizvfio);
            model.put("ADD9PD", add9provdolzh);
            model.put("ADD9PF", add9provfio);
            model.put("ADD9VV", add9vv);
            model.put("ADD9MA", add9mistakeauthor);
            model.put("ADD9DATE1", add9date1);
            model.put("ADD9DATE2", add9date2);
            model.put("ADD9PROTDATE", add9protdate);
            model.put("ADD9RASPNADP", add9raspnadp);
            model.put("ADD9RUKDOLZH", add9rukdolzh);
            model.put("ADD9RUKFIO", add9rukfio);
            model.put("ADD9SUMMRUB", add9summrub);
            model.put("PFIO", pensionerList.get(0).getFio());
        }

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование документов / Пользователь приступил к редактированию документов пенсионера / " + snl + "/ add9edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return ("add9edit");
    }

    @GetMapping("/add1edit")
    public String add1edit(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        Iterable<Klass> klass = klassRepo.findAll();
        model.put("klass", klass);
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        String exist = addsList.get(0).getAdd1exist();
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(ID);
        model.put("add4", add4List);
        model.put("RESULT", add4List.get(0).getResult_final());
        List <Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String snl = pensionerList.get(0).getSnl();
        if (exist == null) {
            String valid = "false";
            model.put("VALID", valid);
            return "editvariants";
        } else {
            model.put("VALID", "true");
            Integer pensid = addsList.get(0).getPensid();
            String add1rd = addsList.get(0).getAdd1reshdate();
            String add1om = addsList.get(0).getAdd1opismistake();
            String add1st = addsList.get(0).getAdd1statja();
            String add1m = addsList.get(0).getAdd1mistake();
            String add1vr = addsList.get(0).getAdd1viprazn();

            model.put("ID", pensid);
            model.put("ADD1VR", add1vr);
            model.put("ADD1OM", add1om);
            model.put("ADD1RD", add1rd);
            model.put("ADD1ST", add1st);
            model.put("ADD1M", add1m);
            model.put("PFIO", pensionerList.get(0).getFio());
        }

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование документов / Пользователь приступил к редактированию документов пенсионера / " + snl + "/ add1edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        return ("add1edit");
    }

    @GetMapping("/add3edit")
    public String add3edit(@AuthenticationPrincipal User user, HttpServletRequest request, Map<String, Object> model, HttpSession session) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        List<Adds> addsList = addsRepo.findAddsByPensid(ID);
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String snl = pensionerList.get(0).getSnl();
        String exist = addsList.get(0).getAdd3exist();
        Iterable<Klass> klass = klassRepo.findAll();
        model.put("klass", klass);
        if (exist == null) {
            String valid = "false";
            model.put("VALID", valid);
            return "editvariants";
        } else {
            model.put("VALID", "true");
            Integer pensid = addsList.get(0).getPensid();
            String add3date = addsList.get(0).getAdd3date();
            String block1date = addsList.get(0).getBlock1date();
            String blocks = addsList.get(0).getBlocks();
            String block1fio = addsList.get(0).getBlock1fio();
            String block1num = addsList.get(0).getBlock1num();
            String block1snl = addsList.get(0).getBlock1snl();
            String block1vd = addsList.get(0).getBlock1vd();
            String block2fio = addsList.get(0).getBlock2fio();
            String block2snl = addsList.get(0).getBlock2snl();
            String block2vd = addsList.get(0).getBlock2vd();
            String block3date = addsList.get(0).getBlock3date();
            String block3klass = addsList.get(0).getKlass();
            String block3razmer = addsList.get(0).getBlock3razmer();
            String block3razmertverd = addsList.get(0).getBlock3razmertverd();
            String block3vp = pensionerList.get(0).getVp();
            String fio = pensionerList.get(0).getFio();

            model.put("ID", pensid);
            model.put("ADD3D", add3date);
            model.put("BL", blocks);
            model.put("B1D", block1date);
            model.put("B1F", block1fio);
            model.put("B1N", block1num);
            model.put("B1S", block1snl);
            model.put("B1V", block1vd);
            model.put("B2F", block2fio);
            model.put("B2S", block2snl);
            model.put("B2V", block2vd);
            model.put("B3D", block3date);
            model.put("B3K", block3klass);
            model.put("B3R", block3razmer);
            model.put("B3RT", block3razmertverd);
            model.put("B3V", block3vp);
            model.put("F", fio);
            model.put("PFIO", pensionerList.get(0).getFio());
        }

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование документов / Пользователь приступил к редактированию документов пенсионера / " + snl + "/ add3edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return ("add3edit");
    }

    @PostMapping("/add3edit")
    public RedirectView add3edit(HttpServletRequest request, @AuthenticationPrincipal User user, @RequestParam Integer pensid, @RequestParam String add3date, @RequestParam String add3upr, @RequestParam(defaultValue = "") String block1fio, @RequestParam(defaultValue = "") String block1num, @RequestParam(defaultValue = "") String block1date, @RequestParam(defaultValue = "") String block1snl, @RequestParam(defaultValue = "") String block1vd, @RequestParam(defaultValue = "") String block2fio, @RequestParam(defaultValue = "") String block2snl, @RequestParam(defaultValue = "") String block2vd, @RequestParam String block3vp, @RequestParam String klass, @RequestParam String block3date, @RequestParam String block3razmer, @RequestParam String block3razmertverd, @RequestParam String blocks,  Map<String, Object> model, HttpSession session) {
        if (blocks.equals("2")) {
            block1date = "";
            block1fio = "";
            block1num = "";
            block1snl = "";
            block1vd = "";
        } else {
            block2fio = "";
            block2snl = "";
            block2vd = "";
        }
        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd3date(add3date);
        adds.setBlocks(blocks);
        adds.setBlock1fio(block1fio);
        adds.setBlock1num(block1num);
        adds.setBlock1date(block1date);
        adds.setBlock1snl(block1snl);
        adds.setBlock1vd(block1vd);
        adds.setBlock2fio(block2fio);
        adds.setBlock2snl(block2snl);
        adds.setBlock2vd(block2vd);
        adds.setKlass(klass);
        adds.setBlock3date(block3date);
        adds.setBlock3razmer(block3razmer);
        adds.setBlock3razmertverd(block3razmertverd);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Решение о взыскании сумм пенсии отредактировано / " + snils + " / add3edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @PostMapping("/add1edit")
    public RedirectView add1edit(@AuthenticationPrincipal User user, HttpServletRequest request, @RequestParam Integer pensid, @RequestParam String add1reshdate, @RequestParam String add1opismistake, @RequestParam String add1statja, @RequestParam String add1mistake, @RequestParam String add1viptype, @RequestParam String add1viprazn, HttpSession session, Map<String, Object> model) {
        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd1reshdate(add1reshdate);
        adds.setAdd1opismistake(add1opismistake);
        adds.setAdd1statja(add1statja);
        adds.setAdd1mistake(add1mistake);
        adds.setAdd1viptype(add1viptype);
        adds.setAdd1viprazn(add1viprazn);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Решение об обнаружении ошибки отредактировано / " + snils + " / add1edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @PostMapping("/add2edit")
    public RedirectView add2edit(@AuthenticationPrincipal User user, HttpServletRequest request, @RequestParam Integer pensid, @RequestParam String add2raspnadp, @RequestParam String add2rukdolzh, @RequestParam String add2rukfio, @RequestParam String add2protdate, @RequestParam String add2vv, @RequestParam String add2mistakeauthor, @RequestParam String add2date1, @RequestParam String add2date2, @RequestParam(defaultValue = "") String add2summrub, @RequestParam(defaultValue = "") String add2summkop, @RequestParam String add2prich, @RequestParam String add2provdolzh, @RequestParam String add2provfio, @RequestParam String add2proizvdolzh, @RequestParam String add2proizvfio, Map<String, Object> model, HttpSession session) {
        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd2raspnadp(add2raspnadp);
        adds.setAdd2rukdolzh(add2rukdolzh);
        adds.setAdd2rukfio(add2rukfio);
        adds.setAdd2protdate(add2protdate);
        adds.setAdd2vv(add2vv);
        adds.setAdd2date1(add2date1);
        adds.setAdd2date2(add2date2);
        adds.setAdd2summrub(add2summrub);
        adds.setAdd2mistakeauthor(add2mistakeauthor);
        adds.setAdd2prich(add2prich);
        adds.setAdd2provdolzh(add2provdolzh);
        adds.setAdd2proizvdolzh(add2proizvdolzh);
        adds.setAdd2provfio(add2provfio);
        adds.setAdd2proizvfio(add2proizvfio);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Протокол о выявдении ИВСП отредактирован / " + snils + " / add2edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @PostMapping("/add9edit")
    public RedirectView add9edit(@AuthenticationPrincipal User user, HttpServletRequest request,
                                 @RequestParam Integer pensid, @RequestParam String add9raspnadp,
                                 @RequestParam String add9rukdolzh, @RequestParam String add9rukfio,
                                 @RequestParam String add9protdate, @RequestParam String add9vv,
                                 @RequestParam String add9mistakeauthor, @RequestParam String add9date1,
                                 @RequestParam String add9date2,
                                 @RequestParam(defaultValue = "") String add9summrub,
                                 @RequestParam String add9prich, @RequestParam String add9provdolzh,
                                 @RequestParam String add9provfio, @RequestParam String add9proizvdolzh,
                                 @RequestParam String add9proizvfio,
                                 Map<String, Object> model, HttpSession session) {
        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd9raspnadp(add9raspnadp);
        adds.setAdd9rukdolzh(add9rukdolzh);
        adds.setAdd9rukfio(add9rukfio);
        adds.setAdd9protdate(add9protdate);
        adds.setAdd9vv(add9vv);
        adds.setAdd9date1(add9date1);
        adds.setAdd9date2(add9date2);
        adds.setAdd9summrub(add9summrub);
        adds.setAdd9mistakeauthor(add9mistakeauthor);
        adds.setAdd9prich(add9prich);
        adds.setAdd9provdolzh(add9provdolzh);
        adds.setAdd9proizvdolzh(add9proizvdolzh);
        adds.setAdd9provfio(add9provfio);
        adds.setAdd9proizvfio(add9proizvfio);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Протокол о выявдении ИВСП отредактирован / " + snils + " / add9edit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @PostMapping("/pensedit")
    public RedirectView pensedit(@AuthenticationPrincipal User user, HttpServletRequest request, @RequestParam Integer id, @RequestParam String fio, @RequestParam String snl, @RequestParam String nvd, @RequestParam String vp, @RequestParam String vpd, @RequestParam String calendar, @RequestParam String adress, Map<String, Object> model, HttpSession session) {
        Pensioner pensioner = pensRepo.findById(id);//  new Pensioner(fio, snl, nvd, vp, vpd, calendar, adress);
        pensioner.setFio(fio);
        pensioner.setAdress(adress);
        pensioner.setCalendar(calendar);
        pensioner.setNvd(nvd);
        pensioner.setVp(vp);
        pensioner.setVpd(vpd);
        pensioner.setSnl(snl);
        pensRepo.save(pensioner);

        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Редактирование / Данные пользлвателя отредактированы / " + snl + " / pensedit / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("editvariants");
    }

    @GetMapping("/pmis")
    public String pmis(Map<String, Object> model, HttpServletRequest request, HttpSession session) {
        session = request.getSession();
        Integer ID = (Integer) session.getAttribute("id");
        if (ID == null) {
            return "pensvariants";
        } else {
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String FIO = pensionerList.get(0).getFio();
        model.put("FIO", FIO);
        model.put("ID", ID);
        return "pmis";
    }
    }

    @GetMapping("/pfrmis")
    public String pfrmis(@AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        session = request.getSession();
        String clientIP = request.getRemoteAddr();
        Integer ID = (Integer) session.getAttribute("id");
        if (ID == null) {
            Logs logs;
            String username = user.getUsername();
            String description =  "Не выбран пенсионер / Пользователь "+ user.getUsername()+ " выбирает/вводит данные пенсионера / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
            return "pensvariants";
        } else {
            List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
            String FIO = pensionerList.get(0).getFio();
            model.put("FIO", FIO);
            model.put("ID", ID);
            return "pfrmis";
        }
    }

    @RequestMapping("/perform_logout")
    public void perform_logout(@AuthenticationPrincipal User user, Map<String, Object> model, HttpServletRequest request) {
            Logs logs;
            String username = user.getUsername();
            String description =  "Выход / Пользователь "+ user.getUsername()+ " завершил сеанс" ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
        }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user, Map<String, Object> model) {
      String user_tel = user.getUser_tel();
      String role = user.getRole();
            if (user_tel == null){
            return  "redirect:/phoneadd";
        } else{
                model.put("ROLE", role);
            model.put("FIO", user.getUser_fio());
            model.put("UNAME", user.getUsername());
        return "main";
        }
    }

    @GetMapping("/phoneadd")
    public String phoneadd(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session){
        Logs logs;
        String clientIP = request.getRemoteAddr();
        String username = user.getUsername();
        String description =  "Авторизация / Новый пользователь "+ user.getUsername()+ " вводит номер телефона / " +clientIP ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);
        model.put("ID", user.getUserid());
        model.put("UCODE", user.getUprcode());
        return "phoneadd";
    }

    @PostMapping("/phoneadd")
    public RedirectView phoneadd(@AuthenticationPrincipal User user, HttpServletRequest request, String userid, String user_tel, Map<String, Object> model){
        user.setUser_tel(user_tel);
        User user1 = userRepo.findByUserid(userid);
        user1.setUser_tel(user_tel);
        userRepo.save(user1);
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Авторизация / Добавлен номер телефона / " + username + " / phoneadd / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("main");
    }

    @GetMapping("/session")
    public String session(Map<String, Object> model, HttpSession httpSession) {
        Object art = httpSession.getAttribute("user");
        return art + httpSession.getId();
    }

    @GetMapping("/mistakes")
    public String mistakes(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session) {
        Integer ID = (Integer) session.getAttribute("id");
        String clientIP = request.getRemoteAddr();
        if (ID == null) {
            Logs logs;
            String username = user.getUsername();
            String description =  "Не выбран пенсионер / Пользователь "+ user.getUsername()+ " выбирает/вводит данные пенсионера / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "pensvariants";
        } else {
            List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
            model.put("PFIO", pensionerList.get(0).getFio());
            return "mistakes";
        }
    }

    @PostMapping("/pdata")
    public RedirectView add(@AuthenticationPrincipal User user, HttpServletRequest request, @RequestParam String fio, @RequestParam String fam, @RequestParam String im, @RequestParam String otch, @RequestParam String snl, @RequestParam String nvd, @RequestParam String vp, @RequestParam String vpd, @RequestParam String calendar, @RequestParam String adress, @RequestParam String uprcode, @RequestParam String pensnomer, Map<String, Object> model, HttpSession httpSession) throws UnknownHostException {
        Pensioner pensioner = new Pensioner(fio, fam, im, otch, snl, nvd, vp, vpd, calendar, adress, uprcode, pensnomer);
        messageRepo.save(pensioner);
        HttpSession session = request.getSession();
        List <Pensioner> pensionerList = pensRepo.findBySnlOrderByIdDesc(snl);
        Integer ID = pensionerList.get(0).getId();
        session.setAttribute("id", ID);
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Добавлены данные пенсионера / " + snl + " / pdata / " + clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("pereplats");
    }

    @GetMapping("/pereplats")
    public String pereplats(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        Integer ID = (Integer) session.getAttribute("id");
        if (ID == null) {
            return "pensvariants";
        } else {
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
            Integer pensid = pensionerList.get(0).getId();
            String PFIO = pensionerList.get(0).getFio();
            String snl = pensionerList.get(0).getSnl();

            String upravlenie = user.getUpravlenie();
            model.put("UPRAVLENIE", upravlenie);

            model.put("ID", pensid);
            model.put("PFIO", PFIO);
            // PrintWriter out = response.getWriter();

            String clientIP = request.getRemoteAddr();
            Logs logs;
            String username = user.getUsername();
            String description =  "Работа с переплатами / Пользователь "+ user.getUsername()+ " приступил к расчету переплат / snl = " + snl + " / pereplats / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "pereplats";
        }
    }

    @GetMapping("/add4printpage")
    public String add4printpage(Map<String, Object> model, HttpSession session, HttpServletRequest request){
        Integer ID = (Integer) session.getAttribute("id");
        model.put("ID", ID);
        List <Pensioner> pensionerList = pensRepo.findPensionerById(ID);
        String FIO = pensionerList.get(0).getFio();
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(ID);
        model.put("PFIO", FIO );
        model.put("add4", add4List);
        return "add4printpage";
    }

    @GetMapping("/journals")
    public String journals(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model) {
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Журнал / Пользователь "+ user.getUsername()+ " выбирает журнал для просмотра / " +clientIP ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        String uprcode = user.getUprcode();
        model.put("ucode", uprcode);
        Iterable<Pensioner> pensionerList = pensRepo.findAll();
        model.put("pensioner", pensionerList);
        return "journals";
    }

    @PostMapping("/journals")
    public String journals(@RequestParam String fio, Map<String, Object> model) {
        Iterable<Pensioner> pensionerList = pensRepo.findAll();
        model.put("pensioner", pensionerList);
        return "journals";
    }

    @GetMapping("/pdata")
    public String pdata(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model) {
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Ввод данных пенсионера / Пользователь "+ user.getUsername()+ " приступил к работе с данными пенсионера/"+clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = "\r\n"+new Date()+" / "+username+" / "+description;
        LogToFile.Logi(loggs);

        Iterable<Pensioner> pensioner = messageRepo.findAll();
        model.put("pensioner", pensioner);
        Iterable<Vpd> vpd = vpdRepository.findAll();
        model.put("vpd", vpd);
        Iterable<Vp> vp = vpRepository.findAll();
        model.put("vp", vp);
        String uprcode = user.getUprcode();
        model.put("UCODE", uprcode);
        //   String usertel = userRepo.findByUsername(credentials).getUser_tel();
        //  String upravlenie = userRepo.findByUsername(credentials).getUpravlenie();

        return "pdata";
    }

    @GetMapping("/add1create")
    public String add1create(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session) {
        String clientIP = request.getRemoteAddr();
        Integer ADD1ID = (Integer) session.getAttribute("id");
        if (ADD1ID == null) {
            Logs logs;
            String username = user.getUsername();
            String description =  "Не выбран пенсионер / Пользователь "+ user.getUsername()+ " выбирает/вводит данные пенсионера / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "pensvariants";
        } else {
        List<Pensioner> pensionerList = pensRepo.findPensionerById(ADD1ID);
        String ADD1SNILS = pensionerList.get(0).getSnl();
        String PFIO = pensionerList.get(0).getFio();
        String uprcode = user.getUprcode();
        model.put("ucode", uprcode);
        List<Add4> add4List = add4Repo.findByPensidOrderByViphismonthAsc(ADD1ID);
        model.put("add4", add4List);
        Double RESULT = Math.abs(add4List.get(0).getResult_final());


                String ADD1DATEMISTAKE = pensionerList.get(0).getCalendar();

                String upravlenie = user.getUpravlenie();
                List<Upr> upr1 = uprRepository.findByUpr(upravlenie);
                model.put("upr1", upr1);
                String uprboss = upr1.get(0).getUpr_nach();

                model.put("ADD1ID", ADD1ID);
                model.put("ADD1DATEMISTAKE", ADD1DATEMISTAKE);
                model.put("ADD1SNILS", ADD1SNILS);
                model.put("UPRBOSS", uprboss);
                model.put("RESULT", RESULT);
                model.put("UPRAVLENIE", upravlenie);
                model.put("EXIST", "true");
                model.put("PFIO", PFIO);

            Logs logs;
            String username = user.getUsername();
            String description =  "Создание документов / Пользователь "+ user.getUsername()+ " приступил к созданию документа / " + ADD1SNILS + " / add1create / " + clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

                return "add1create";
            }
        }

    @GetMapping("/add3create")
    public String add3create(HttpServletRequest request, @AuthenticationPrincipal User user, Map<String, Object> model, HttpSession session) {
        String clientIP = request.getRemoteAddr();
        Integer ID = (Integer) session.getAttribute("id");
        if (ID == null) {
            Logs logs;
            String username = user.getUsername();
            String description =  "Не выбран пенсионер / Пользователь "+ user.getUsername()+ " выбирает/вводит данные пенсионера / " +clientIP ;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "pensvariants";
        } else {
            List<Pensioner> pensionerList = pensRepo.findPensionerById(ID);
            Integer PENSID = pensionerList.get(0).getId();
            String ADD3ADRESS = pensionerList.get(0).getAdress();
            String ADD3NVD = pensionerList.get(0).getNvd();
            String ADD3VP = pensionerList.get(0).getVp();
            String ADD3VPD = pensionerList.get(0).getVpd();
            String ADD3DATEMISTAKE = pensionerList.get(0).getCalendar();
            String ADD3FIO = pensionerList.get(0).getFio();
            String ADD3SNILS = pensionerList.get(0).getSnl();

            String upravlenie = user.getUpravlenie();
            String uprcode = user.getUprcode();
            String tel = user.getUser_tel();
            String userfio = user.getUser_fio();
            List<Upr> upr1 = uprRepository.findByUpr(upravlenie);
            String uprfull = upr1.get(0).getUpr_full();
            String uprnach = upr1.get(0).getUpr_nach();
            String upradress = upr1.get(0).getUpr_adress();

            model.put("PENSID", PENSID);
            model.put("ADD3FIO", ADD3FIO);
            model.put("ADD3ADRESS", ADD3ADRESS);
            model.put("ADD3NVD", ADD3NVD);
            model.put("ADD3VP", ADD3VP);
            model.put("ADD3VPD", ADD3VPD);
            model.put("ADD3DATEMISTAKE", ADD3DATEMISTAKE);
            model.put("ADD3SNILS", ADD3SNILS);
            model.put("U", upravlenie);
            model.put("ucode", uprcode);
            model.put("T", tel);
            model.put("USF", userfio);
            model.put("UF", uprfull);
            model.put("UN", uprnach);
            model.put("UA", upradress);
            model.put("PFIO", ADD3FIO);

            Logs logs;
            String username = user.getUsername();
            String description =  "Создание документов / Пользователь "+ user.getUsername()+ " приступил к созданию документа / add3create / " + ADD3SNILS + " / " + clientIP;
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
            LogToFile.Logi(loggs);

            return "add3create";
        }
    }

    @PostMapping("/add1create")
    public RedirectView add1create(@AuthenticationPrincipal User user,
                                   String uprcode, String add1exist, Integer pensid, String add1reshdate, String upr,
                                   String add1opismistake, String add1statja, String add1mistake,
                                   String add1viptype, String add1viprazn, String add2exist, String add2raspnadp,
                                   String add2rukdolzh, String add2rukfio, String add2protdate,
                                   String uprfull, String add2vv, String add2date1, String add2date2,
                                   String add2summrub, String add2mistakeauthor,
                                   String add2prich, String add2provdolzh, String add2proizvdolzh,
                                   String add2provfio, String add2proizvfio, String add5exist,
                                   String upradress, String tel, String uprnach, String uprisp,
                                   String add3exist, String add3date, String blocks,
                                   String block1fio, String block1num, String block1date, String block1snl,
                                   String block1vd, String block2fio, String block2snl, String block2vd,
                                   String klass, String block3date, String block3razmer,
                                   String block3razmertverd, String add6exist,
                                   String add9exist, String add9raspnadp, String add9rukdolzh, String add9rukfio,
                                   String add9protdate, String add9vv, String add9date1, String add9date2,
                                   String add9summrub, String add9mistakeauthor, String add9prich,
                                   String add9provdolzh, String add9proizvdolzh, String add9provfio, String add9proizvfio,
                                   Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        Adds adds = new Adds(uprcode, add1exist, pensid, add1reshdate, upr, add1opismistake, add1statja,
                add1mistake, add1viptype, add1viprazn, add2exist, add2raspnadp, add2rukdolzh,
                add2rukfio, add2protdate,  uprfull, add2vv, add2date1, add2date2,
                add2summrub, add2mistakeauthor, add2prich, add2provdolzh,
                add2proizvdolzh, add2provfio, add2proizvfio, add5exist, upradress, tel, uprnach,
                uprisp, add3exist, add3date, blocks, block1fio, block1num,
                block1date, block1snl, block1vd, block2fio, block2snl, block2vd,
                klass, block3date, block3razmer, block3razmertverd, add6exist, add9exist, add9raspnadp, add9rukdolzh,
                add9rukfio, add9protdate, add9vv, add9date1, add9date2,
                add9summrub, add9mistakeauthor, add9prich,
                add9provdolzh, add9proizvdolzh, add9provfio, add9proizvfio);
        adds.setAdd1exist(add1exist);
        adds.setPensid(pensid);
        adds.setAdd1reshdate(add1reshdate);
        adds.setUpr(upr);
        adds.setAdd1opismistake(add1opismistake);
        adds.setAdd1statja(add1statja);
        adds.setAdd1mistake(add1mistake);
        adds.setAdd1viprazn(add1viprazn);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Создано решение об обнаружении ошибки / " + snils + " / add1create / " +clientIP;
        logs = new Logs(new Date(), username, description);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        logsRepo.save(logs);

        return new RedirectView("add2create");
    }

    @PostMapping("/add2create")
    public RedirectView add2create(@AuthenticationPrincipal User user,
                                   Integer pensid, String add2exist, String add2raspnadp, String add2rukdolzh,
                                   String add2rukfio, String add2protdate, String uprfull, String add2vv,
                                   String add2date1, String add2date2, String add2summrub,
                                   String add2mistakeauthor, String add2prich, String add2provdolzh,
                                   String add2proizvdolzh, String add2provfio, String add2proizvfio, String add5exist,
                                   String upradress,String tel, String uprisp,
                                   Map<String, Object> model, HttpSession session, HttpServletRequest request) {

        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd2exist("true");
        adds.setAdd2raspnadp(add2raspnadp);
        adds.setAdd2rukdolzh(add2rukdolzh);
        adds.setAdd2rukfio(add2rukfio);
        adds.setAdd2protdate(add2protdate);
        adds.setUprfull(uprfull);
        adds.setAdd2vv(add2vv);
        adds.setAdd2date1(add2date1);
        adds.setAdd2date2(add2date2);
        adds.setAdd2summrub(add2summrub);
        adds.setAdd2mistakeauthor(add2mistakeauthor);
        adds.setAdd2prich(add2prich);
        adds.setAdd2provdolzh(add2provdolzh);
        adds.setAdd2proizvdolzh(add2proizvdolzh);
        adds.setAdd2provfio(add2provfio);
        adds.setAdd2proizvfio(add2proizvfio);
        adds.setAdd5exist("true");
        adds.setUpradress(upradress);
        adds.setTel(tel);
        adds.setUprisp(uprisp);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Создан протокол о выявлении ИВСП / " + snils + " / add2create / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("afterprintvariants");


    }

    @PostMapping("/add9create")
    public RedirectView add9create(@AuthenticationPrincipal User user,
                                   Integer pensid, String add9exist, String add9raspnadp, String add9rukdolzh,
                                   String add9rukfio, String add9protdate, String uprfull, String add9vv,
                                   String add9date1, String add9date2, String add9summrub,
                                   String add9mistakeauthor, String add9prich, String add9provdolzh,
                                   String add9proizvdolzh, String add9provfio, String add9proizvfio,
                                   Map<String, Object> model, HttpSession session, HttpServletRequest request) {

        Adds adds = addsRepo.findByPensid(pensid);
        adds.setAdd9exist("true");
        adds.setAdd9raspnadp(add9raspnadp);
        adds.setAdd9rukdolzh(add9rukdolzh);
        adds.setAdd9rukfio(add9rukfio);
        adds.setAdd9protdate(add9protdate);
        adds.setUprfull(uprfull);
        adds.setAdd9vv(add9vv);
        adds.setAdd9date1(add9date1);
        adds.setAdd9date2(add9date2);
        adds.setAdd9summrub(add9summrub);
        adds.setAdd9mistakeauthor(add9mistakeauthor);
        adds.setAdd9prich(add9prich);
        adds.setAdd9provdolzh(add9provdolzh);
        adds.setAdd9proizvdolzh(add9proizvdolzh);
        adds.setAdd9provfio(add9provfio);
        adds.setAdd9proizvfio(add9proizvfio);
        addsRepo.save(adds);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Создан протокол о выявлении ИВСП / " + snils + " / add9create / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("afterprintvariants");


    }

    @PostMapping("/add3create")
    public RedirectView add3create(@AuthenticationPrincipal User user,
                                   String uprcode, String add1exist, Integer pensid, String add1reshdate, String upr,
                                   String add1opismistake, String add1statja, String add1mistake,
                                   String add1viptype, String add1viprazn, String add2exist, String add2raspnadp,
                                   String add2rukdolzh, String add2rukfio, String add2protdate,
                                   String uprfull, String add2vv, String add2date1, String add2date2,
                                   String add2summrub, String add2mistakeauthor,
                                   String add2prich, String add2provdolzh, String add2proizvdolzh,
                                   String add2provfio, String add2proizvfio, String add5exist,
                                   String upradress, String tel, String uprnach, String uprisp,
                                   String add3exist, String add3date, String blocks,
                                   String block1fio, String block1num, String block1date, String block1snl,
                                   String block1vd, String block2fio, String block2snl, String block2vd,
                                   String klass, String block3date, @RequestParam(defaultValue = "") String block3razmer,
                                   @RequestParam(defaultValue = "") String block3razmertverd, String add6exist,
                                   String add9exist, String add9raspnadp, String add9rukdolzh, String add9rukfio,
                                   String add9protdate, String add9vv, String add9date1, String add9date2,
                                   String add9summrub, String add9mistakeauthor, String add9prich,
                                   String add9provdolzh, String add9proizvdolzh, String add9provfio, String add9proizvfio,
                                   Map<String, Object> model, HttpSession session, HttpServletRequest request){
        if (blocks.equals("2")) {
            block1date = "";
            block1fio = "";
            block1num = "";
            block1snl = "";
            block1vd = "";
        } else {
            block2fio = "";
            block2snl = "";
            block2vd = "";
        }
        Adds adds = addsRepo.findByPensid(pensid);
        if (adds == null) {
            adds = new Adds(uprcode, add1exist, pensid, add1reshdate, upr, add1opismistake, add1statja,
                    add1mistake, add1viptype, add1viprazn, add2exist, add2raspnadp, add2rukdolzh,
                    add2rukfio, add2protdate,  uprfull, add2vv, add2date1, add2date2,
                    add2summrub, add2mistakeauthor, add2prich, add2provdolzh,
                    add2proizvdolzh, add2provfio, add2proizvfio, add5exist, upradress, tel, uprnach,
                    uprisp, add3exist, add3date, blocks, block1fio, block1num,
                    block1date, block1snl, block1vd, block2fio, block2snl, block2vd,
                    klass, block3date, block3razmer, block3razmertverd, add6exist, add9exist, add9raspnadp, add9rukdolzh,
                    add9rukfio, add9protdate, add9vv, add9date1, add9date2,
                    add9summrub, add9mistakeauthor, add9prich,
                    add9provdolzh, add9proizvdolzh, add9provfio, add9proizvfio);
            adds.setPensid(pensid);
            setAdds(add3date, blocks, block1fio, block1num, block1date, block1snl, block1vd, block2fio,
                    block2snl, block2vd, klass, block3date, block3razmer, block3razmertverd, add6exist, adds);
        } else {
            setAdds(add3date, blocks, block1fio, block1num, block1date, block1snl, block1vd, block2fio,
                    block2snl, block2vd, klass, block3date, block3razmer, block3razmertverd, add6exist, adds);
        }

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Создано решение о взыскании сумм пенсии / " + snils + " / add3create / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("add9create");
    }

    private void setAdds(String add3date, String blocks, String block1fio, String block1num,
                         String block1date, String block1snl, String block1vd, String block2fio,
                         String block2snl, String block2vd, String klass, String block3date,
                         String block3razmer, String block3razmertverd, String add6exist, Adds adds) {
        adds.setAdd3exist("true");
        adds.setAdd3date(add3date);
        adds.setBlocks(blocks);
        adds.setBlock1fio(block1fio);
        adds.setBlock1num(block1num);
        adds.setBlock1date(block1date);
        adds.setBlock1snl(block1snl);
        adds.setBlock1vd(block1vd);
        adds.setBlock2fio(block2fio);
        adds.setBlock2snl(block2snl);
        adds.setBlock2vd(block2vd);
        adds.setKlass(klass);
        adds.setBlock3date(block3date);
        adds.setBlock3razmer(block3razmer);
        adds.setBlock3razmertverd(block3razmertverd);
        adds.setAdd6exist(add6exist);
        addsRepo.save(adds);
    }

    @PostMapping("/pereplats")
    public RedirectView add(@AuthenticationPrincipal User user, @RequestParam String upr, @RequestParam String[] viphismonth, @RequestParam String[] nach_summ, @RequestParam String[] vip_razn, @RequestParam String[] vip_summ, @RequestParam Double result_final, @RequestParam Integer pensid, Map<String, Object> model, HttpServletRequest request, HttpSession session) {
        Arrays.sort(viphismonth);
        System.out.println(viphismonth);

        for (int i = 0; i < viphismonth.length; i++) {
            Add4 add4 = new Add4(upr, viphismonth[i], vip_summ[i], nach_summ[i], vip_razn[i], result_final, pensid);
            add4Repo.save(add4);
        }

        session.setAttribute("id", pensid);
        String RESULT = request.getParameter("result_final");
        session.setAttribute("result", RESULT);

        Pensioner pensioner = pensRepo.findById(pensid);
        String snils = pensioner.getSnl();
        String clientIP = request.getRemoteAddr();
        Logs logs;
        String username = user.getUsername();
        String description =  "Создание документов / Создан расчет ИВСП / " + snils + " / pereplats / " +clientIP;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);

        String loggs = ("\r\n" + new Date() + " / " + username + " / " + description);
        LogToFile.Logi(loggs);

        return new RedirectView("mistakes");
    }

    @PostMapping("/pfrmis")
    public String pfrmis(@RequestParam String fio, Map<String, Object> model) {
        Iterable<Pensioner> pensionerList = pensRepo.findAll();
        model.put("pensioner", pensionerList);
        Iterable<Add4> add4List = add4Repo.findAll();
        model.put("add4", add4List);
        return "pfrmis";
    }

    @PostMapping("/pmis")
    public String pmis(@RequestParam String fio, Map<String, Object> model) {
        Iterable<Pensioner> pensionerList = pensRepo.findAll();
        model.put("pensioner", pensionerList);
        return "pmis";
    }


    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Pensioner> pensioner;
        if (filter != null && !filter.isEmpty()) pensioner = messageRepo.findBySnl(filter);
        else pensioner = messageRepo.findAll();
        model.put("pensioner", pensioner);

        return "pdata";
    }


     /*@RequestMapping("/save") public String process(){
     vpdRepository.save(new Vpd("ВОЗОБНОВЛЕНИЕ", "ВОЗОБНОВЛЕНИИ"));
     vpdRepository.save(new Vpd("ВОССТАНОВЛЕНИЕ", "ВОССТАНОВЛЕНИИ"));
     vpdRepository.save(new Vpd("ИНДЕКСАЦИЯ", "ИНДЕКСАЦИИ"));
     vpdRepository.save(new Vpd("КОРРЕКТИРОВКА", "КОРРЕКТИРОВКЕ"));
     vpdRepository.save(new Vpd("НАЗНАЧЕНИЕ", "НАЗНАЧЕНИИ"));
     vpdRepository.save(new Vpd("ОСУЩЕСТВЛЕНИЕ ВЫПЛАТЫ", "ОСУЩЕСТВЛЕНИИ ВЫПЛАТЫ"));
     vpdRepository.save(new Vpd("ОТМЕНА РЕШЕНИЯ", "ОТМЕНЕ РЕШЕНИЯ"));
     vpdRepository.save(new Vpd("ПЕРЕВОД", "ПЕРЕВОДЕ"));
     vpdRepository.save(new Vpd("ПЕРЕРАСЧЕТ", "ПЕРЕРАСЧЕТЕ"));
     vpdRepository.save(new Vpd("ПРЕКРАЩЕНИЕ", "ПРЕКРАЩЕНИИ"));
     vpdRepository.save(new Vpd("ПРИОСТАНОВЛЕНИЕ", "ПРИОСТАНОВЛЕНИИ"));
     vpdRepository.save(new Vpd("ПРОДЛЕНИЕ", "ПРОДЛЕНИИ"));
     return "Done";
     }*/

    @RequestMapping("/findallvpd")
    public @ResponseBody
    String findAllvpd() {
        String result = "<mustache>";

        for (Vpd vpd : vpdRepository.findAll()) result += "<div>" + vpd.toString() + "</div>";

        return result + "</mustache>";
    }

    @RequestMapping("/findvpdbyid")
    public @ResponseBody
    String findByVpdId(@RequestParam("id") long id) {
        String result = "";
        result = vpdRepository.findById(id).toString();
        return result;
    }


    @RequestMapping("/findbyvpd")
    public @ResponseBody
    String findByVpd(@RequestParam("vpd") String vpd) {
        String result = "";
        result = vpdRepository.findByVpd(vpd).toString();
        return result;
    }


     /*@RequestMapping("/vpsave") public String process(){
     vpRepository.save(new Vp("По старости"));
     vpRepository.save(new Vp("Социальная"));
     vpRepository.save(new Vp("По инвалидности"));
     vpRepository.save(new Vp("По случаю потери кормильца"));
     vpRepository.save(new Vp("За выслугу лет"));
     vpRepository.save(new Vp("По старости - Центр занятости"));
     vpRepository.save(new Vp("Персональная"));
     vpRepository.save(new Vp("По старости - Народным депутатам"));
     return "Done";
     }*/

    @RequestMapping("/findallvp")
    public @ResponseBody
    String findAllvp() {
        String result = "<mustache>";

        for (Object vp : vpRepository.findAll()) result += "<div>" + vp.toString() + "</div>";

        return result + "</mustache>";
    }

    @RequestMapping("/findbyupr")
    public @ResponseBody
        //Pensioner findByFio(@RequestParam("search_name") String fio) {
    Upr findByUpr(@RequestParam("upr") String upr) {
        List<Upr> result = uprRepository.findByUpr(upr);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @RequestMapping("/findvpbyid")
    public @ResponseBody
    String findByVpId(@RequestParam("id") long id) {
        String result = "";
        result = vpRepository.findById(id).toString();
        return result;
    }

    @RequestMapping("/findbyvp")
    public @ResponseBody
    String findByVp(@RequestParam("vp") String vp) {
        String result = "";
        result = vpRepository.findByVp(vp).toString();
        return result;
    }

    @RequestMapping("/findbyfio")
    public @ResponseBody
        //Pensioner findByFio(@RequestParam("search_name") String fio) {
    Pensioner findByFio(@RequestParam("fio") String fio) {
        List<Pensioner> result = pensRepo.findByFio(fio);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }


    @RequestMapping("/findbyptksnl")
    public @ResponseBody
        //Pensioner findByFio(@RequestParam("search_name") String fio) {
    PtkPensioner findPtkPensionerBySnils(@AuthenticationPrincipal User user, @RequestParam("snils") String snils) {
        List<PtkPensioner> result = rosDao.findPtkBySnils(snils);
        if (result.isEmpty()) {
            return null;
        }
        Logs logs;
        String username = user.getUsername();
        String description =  "Запрос информации / Запрошены данные на пенсионера / " + snils + " / pdata/findptkbysnl";
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        return result.get(0);
    }

    @RequestMapping("/findbypensid")
    public @ResponseBody
        //Pensioner findByFio(@RequestParam("search_name") String fio) {
    Adds findByPensid(@RequestParam("pensid") Integer pensid) {
        List<Adds> result = addsRepo.findAddsByPensid(pensid);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @RequestMapping("/findbyptkfio")
    public @ResponseBody
    List<PtkPensioner> findPtkByName(String rep_up_fio) {
        return rosDao.findPtkByName(rep_up_fio);
    }

    @RequestMapping("/delete")
    public RedirectView delete(@AuthenticationPrincipal User user){
        logsRepo.deleteAll();
        Logs logs;
        String username = user.getUsername();
        String description =  "Журнал очищен";
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        return new RedirectView("logs");
    }

/*    @DeleteMapping("/deletebypensid")
    public RedirectView deletebypensid(@AuthenticationPrincipal User user){
        addsRepo.delete(pensid);
        addsRepo.deleteByPensid(pensid);
    }*/

    @RequestMapping("/clearattempts")
    public RedirectView clearattempts(@AuthenticationPrincipal User user, String uname){
        User user1 = userRepo.findUserByUsername(uname);
        user1.setActive(0);
        userRepo.save(user1);
        Logs logs;
        String username = user.getUsername();
        String description =  "Обнуление попыток / Для пользователя "+user1.getUsername()+ " попытки обнулены" ;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        return new RedirectView("attempts");
    }

    @RequestMapping("/edittries")
    public RedirectView edittries(@AuthenticationPrincipal User user, Long kolpopitok, Long block, Long koefpopitok){
        Adminparam adminparam = adminparamRepo.findById(1L);
        adminparam.setKolpopitok(kolpopitok);
        adminparam.setBlock(block);
        adminparam.setKoefpopitok(koefpopitok);
        adminparamRepo.save(adminparam);
        Logs logs;
        String username = user.getUsername();
        String description =  "Изменение попыток / Настройки попыток доступа изменены / " + username;
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
        return new RedirectView("tries");
    }

    @GetMapping("/successlogout")
    public void successlogout(@AuthenticationPrincipal User user){
        Logs logs;
        String username = user.getUsername();
        String description =  "Выход / Пользователь " + username + " завершил сеанс работы / logout ";
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);
    }

    //@RequestMapping("/findbyptksnl")
    // public @ResponseBody
    // List<PtkPensioner> findPtkBySnils(String snils) {
    //    return rosDao.findPtkBySnils(snils);
    //}

    @RequestMapping("/findpensbyid")
    public @ResponseBody
    String findPensById(@RequestParam("id") Integer id) {
        String result = "";
        result = pensRepo.findById(id).toString();
        return result;
    }


  /*  @Bean(name = "rosDataSourceTest")      // 3
    public DataSource asvDataSourceTest() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:db2://10.41.31.33:50000/ASV");
        dataSource.setUsername("db2inst");
        dataSource.setPassword("db2inst");
        dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        return dataSource;
    }
    */
    //  @Autowired
    //  public void setPtkRepository(PtkRepository ptkRepository) {
    //     this.ptkRepository = ptkRepository;
    // }

    /*@RequestMapping(value = "/findallp", method = RequestMethod.GET)
    public @ResponseBody
    String findAllp() {
        StringBuilder sb = new StringBuilder();
        sb.append("<mustache><table border=1>");
        //String result = "<mustache>";
        sb.append("<tr>");
        sb.append("<td>").append("ФИО").append("</td>");
        sb.append("<td>").append("СНИЛС").append("</td>");
        sb.append("<td>").append("№ выплатного дела").append("</td>");
        sb.append("<td>").append("Вид пенсии").append("</td>");
        sb.append("<td>").append("Вид пенсионного действия").append("</td>");
        sb.append("<td>").append("Дата ошибочной пенсии").append("</td>");
        sb.append("</tr>");
        for (Pensioner pensioner : pensRepo.findAll()) {

            sb.append("<tr>");
            sb.append("<td>").append(pensioner.getFio()).append("</td>");
            sb.append("<td>").append(pensioner.getSnl()).append("</td>");
            sb.append("<td>").append(pensioner.getNvd()).append("</td>");
            sb.append("<td>").append(pensioner.getVp()).append("</td>");
            sb.append("<td>").append(pensioner.getVpd()).append("</td>");
            sb.append("<td>").append(pensioner.getDate()).append("</td>");
            sb.append("</tr>");
            sb.append("<tr>");
            sb.append("<td>").append(pensioner.getFio()).append("</td>");
            sb.append("/tr");
            //result += "<div>" + pensioner.toString() + "</div>";
        }

        return sb.toString() + "</table></mustache>";
    }
*/
    @RequestMapping(value = "/findallp", method = RequestMethod.GET)
    public @ResponseBody
    String findAllp(@AuthenticationPrincipal User user) {
        String uprcode = user.getUprcode();
        StringBuilder sb = new StringBuilder();
        sb.append("<mustache><table id=\"pens_data\" border=5>");
        //String result = "<mustache>";
        sb.append("<tr>");
        sb.append("<th align=center>").append("<b>ФИО</b>").append("</th>");
        sb.append("<th align=center>").append("<b>СНИЛС</b>").append("</th>");
        sb.append("<th align=center>").append("<b>№ выплатного дела</b>").append("</th>");
        sb.append("<th align=center>").append("<b>Вид пенсии</b>").append("</th>");
        sb.append("<th align=center>").append("<b>Вид пенсионного действия</b>").append("</th>");
        sb.append("<th align=center>").append("<b>Дата установления с ошибкой</b>").append("</th>");
        sb.append("<th align=center>").append("<b>Адрес пенсионера</b>").append("</th>");
        sb.append("</tr>");
        for (Pensioner pensioner : pensRepo.findAllByUprcodeOrderByIdAsc(uprcode)) {

            sb.append("<tr>");
            sb.append("<td align=center>").append(pensioner.getFio()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getSnl()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getNvd()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getVp()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getVpd()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getCalendar()).append("</td>");
            sb.append("<td align=center>").append(pensioner.getAdress()).append("</td>");
            sb.append("<td align=center>").append("<button type=\"button\" id=\"editBtn\" style=\"padding: 5px\"><b>Редактировать</b></button>").append("</td>");
            sb.append("</tr>");
            //result += "<div>" + pensioner.toString() + "</div>";
        }
        sb.append("</table>");
        sb.append("<script src=\"js/script_edit_table.js\"></script>");
        sb.append("<table>");
        sb.append("<tr>");
        sb.append("<td colspan=7>").append("</td>");
        sb.append("<td align=center>").append("<form action=\"getPensFile.xls\" style=\"margin: 0;\">").append("<button type=\"submit\" style=\"padding: 5px\"><b>Получить файл</b></button>").append("</form").append("</td>");
        sb.append("</tr>");
        return sb.toString() + "</table></mustache>";
    }

}

