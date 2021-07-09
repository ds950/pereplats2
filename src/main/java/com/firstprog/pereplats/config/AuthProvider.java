package com.firstprog.pereplats.config;

//Этот файл используется для авторизации через внутренний сервер аутентификации


import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstprog.pereplats.Application;
import com.firstprog.pereplats.controller.AdminparamService;
import com.firstprog.pereplats.controller.LogToFile;
import com.firstprog.pereplats.domain.Adminparam;
import com.firstprog.pereplats.domain.Logs;
import com.firstprog.pereplats.domain.Upr;
import com.firstprog.pereplats.domain.User;
import com.firstprog.pereplats.repos.LogsRepo;
import com.firstprog.pereplats.repos.UprRepository;
import com.firstprog.pereplats.repos.UserRepo;
import jodd.http.HttpUtil;
import jodd.http.HttpValuesMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthProvider implements AuthenticationProvider {
    private static String ADR = "http://....";
    @Autowired
    private static final Logger logger = LogManager.getLogger(Application.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LogsRepo logsRepo;
    @Autowired
    private UprRepository uprRepository;
    @Autowired
    private AdminparamService adminparamService;
    @Autowired
    HttpSession session;
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        logger.info("AuthProvider authenticate()");
        SecurityContext context = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) a;
        String username = String.valueOf(auth.getPrincipal());
        String password = String.valueOf(auth.getCredentials());
        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("adr", "http://127.0.0.0/Autent");
        parameterList.put("kod", "162");
        parameterList.put("login", username);
        logger.info("Попытка авторизации по логину " + username + " AuthProvider authenticate()");
        parameterList.put("pass", password);
        CloseableHttpResponse httpResponse = getHTTPResponse("http://.../AutentAll",
                parameterList);
        Header[] headers = httpResponse.getHeaders("Location");

        User user2;
        try {
            user2 = userRepo.findByUsername(username).get();
        }
        catch (Exception e){
            user2 = new User(username);
            userRepo.save(user2);
        }

        Adminparam adminparam = adminparamService.findByAdminparam();

        Long datenow = new Date().getTime()+10800000l; //избавление от погрешности во времени
        //long t = datenow - logerr.getDate().getTime();
        //boolean bbb = (datenow - logerr.getDate().getTime()) <= 600000l;
        if(user2.getActive()>=adminparam.getKolpopitok() && user2.getActive()<adminparam.getBlock() &&
                (datenow - user2.getDate().getTime()) <=
                        (600000l + ((adminparam.getKolpopitok()-2)*adminparam.getKoefpopitok()*60000l))){
            user2.setActive(user2.getActive()+1);
            user2.setDate(new Date(datenow));
            userRepo.save(user2);
            Logs logs;
            String description = "Попытка авторизации / превышен лимит попыток количество попыток"
                    + user2.getActive()+" AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            logger.info("Попытка авторизации по логину "+username+" Превышен лимит попыток " +
                    "  Количество попыток "+user2.getActive()+" AuthProvider authenticate()");
            throw new BadCredentialsException("Превышен лимит попыток");
        }
        if(user2.getActive()>=adminparam.getBlock()){
            Logs logs;
            String description =  "Попытка авторизации / пользователь заблокирован количество попыток"
                    + user2.getActive()+" AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
            logger.info("Попытка авторизации по логину "+username+" Пользователь заблокирован" +
                    " Количество попыток "+user2.getActive()+"  AuthProvider authenticate()");
            throw new BadCredentialsException("Пользователь заблокирован");
        }

        if (headers.length == 0) {
            user2.setActive(user2.getActive()+1);
            user2.setDate(new Date(datenow));
            userRepo.save(user2);
            Logs logs;
            String description =  "Попытка авторизации / пользователь ввел неправильный пароль / количество попыток - "
                    + user2.getActive()+" / AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
            throw new BadCredentialsException("Пароль неверен");
        } else {
            user2.setActive(0);
            user2.setDate(new Date(datenow));
            userRepo.save(user2);
        }
        String response = headers[0].getValue();
        Matcher authQueryString = Pattern
                .compile("^http://127\\.0\\.0\\.0/Autent\\?([^\\r\\n]++)$")
                .matcher(response);
        if (!authQueryString.find()) {
            Logs logs;
            String description =  "Попытка авторизации / пользователь ввел неправильный пароль / количество попыток - "
                    + user2.getActive()+" / AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);
            throw new BadCredentialsException("Пароль неверен");
        }
        HttpValuesMap<Object> authData = HttpUtil.parseQuery(authQueryString.group(1), true);
        Collection<GrantedAuthority> roleList = new HashSet<>();
        Object[] rights = authData.get("right");
        String userId = (String) authData.get("id")[0];
        String upfrCode = (String) authData.get("upfr")[0];
        String fio = getFIOfromACS(userId);
        String role = null;

        for (int i = 0; i < rights.length; i++) {
            if ("0".equals(rights[i])) {
                role = "999";
                break;
            } else{
                role = "1";
            }
        }

//        if (upfrCode.equals("0")){
//            SimpleGrantedAuthority authRole = new SimpleGrantedAuthority("ROLE_OPFR");
//            roleList.add(authRole);
//        }
//        else {
//            SimpleGrantedAuthority authRole = new SimpleGrantedAuthority("ROLE_UPFR");
//            roleList.add(authRole);
//        }
        User user = new User();
        user.setActive(1);
        user.setUserid(userId);
        user.setUsername(username);
        user.setUser_fio(fio);
        user.setUprcode(upfrCode);
        user.setRole(role);
        List<Upr> uprList = uprRepository.findByUprcode(upfrCode);
        String upr = uprList.get(0).getUpr();
        user.setUpravlenie(upr);
        User userList = userRepo.findByUsername(username).get();
        if (userList.getUserid() == null) {
            userList.setUserid(userId);
            userList.setUprcode(upfrCode);
            userRepo.save(userList);
            a = new UsernamePasswordAuthenticationToken(user, "", roleList);
            context.setAuthentication(a);
            Logs logs;
            String description =  "Попытка авторизации / пользователь " +username+ " авторизован / количество попыток - "
                    + user2.getActive()+" / AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description + " / " + " AuthProvider / ");
            LogToFile.Logi(loggs);

            logger.info("Пользователь " + user.getUsername() + " авторизован  AuthProvider authenticate()end");
            return a;
        } else {
            if(userList.getUser_tel()!=null){
                user.setUser_tel(userList.getUser_tel());
                a = new UsernamePasswordAuthenticationToken(user, "", roleList);
                context.setAuthentication(a);
                Logs logs;
                String description =  "Попытка авторизации / пользователь " +username+ " авторизован / количество попыток - "
                        + user2.getActive()+" / AuthProvider authenticate()";
                logs = new Logs(new Date(), username, description);
                logsRepo.save(logs);

                String loggs = ("\r\n" + new Date() + " / " + username + " / " + description + " / " + " AuthProvider / ");
                LogToFile.Logi(loggs);

                logger.info("Пользователь " + user.getUsername() + " авторизован  AuthProvider authenticate()end");
                return a;
            }

            a = new UsernamePasswordAuthenticationToken(user, "", roleList);
            context.setAuthentication(a);
            Logs logs;
            String description =  "Попытка авторизации / пользователь "+username+" авторизован / количество попыток - "
                    + user2.getActive()+" / AuthProvider authenticate()";
            logs = new Logs(new Date(), username, description);
            logsRepo.save(logs);

            String loggs = ("\r\n" + new Date() + " / " + username + " / " + description + " / " + " AuthProvider / ");
            LogToFile.Logi(loggs);

            logger.info("Пользователь " + user.getUsername() + " авторизован  AuthProvider authenticate()end");
            return a;
        }
    }

    private String sendHTTPRequest(String addr, Map<String, String> parameterList) {
        try {
            CloseableHttpResponse response = getHTTPResponse(addr, parameterList);
            HttpEntity entity = response.getEntity();
            return new String(ISToByte(entity.getContent()), "UTF8");
        } catch (IOException ex) {
            return null;
        }
    }
    private static byte[] ISToByte(InputStream is) {
        try {
            List<Byte[]> L = new ArrayList<>();
            byte[] buffer = new byte[1024];
            int len, len_all = 0;
            while ((len = is.read(buffer)) > 0) {
                Byte[] B = new Byte[len];
                for (int i = 0; i < len; i++) {
                    B[i] = buffer[i];
                }
                L.add(B);
                len_all += len;
            }
            buffer = new byte[len_all];
            for (int i = 0, j = 0, k; i < L.size(); i++) {
                Byte[] B = L.get(i);
                for (k = 0; k < B.length; k++, j++) {
                    buffer[j] = B[k];
                }
            }
            return buffer;
        } catch (IOException ex) {
            return null;
        }
    }


    private String getFIOfromACS(String userId) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", userId);
        String answer = sendHTTPRequest(AuthProvider.ADR + "getFIO", parameters);
        ObjectMapper om = new ObjectMapper();
        String F = "", I = "", O = "";
        try {
            F = om.readTree(om.readTree(answer).findValue("fio").get(0).textValue()).get("F").textValue();
            I = om.readTree(om.readTree(answer).findValue("fio").get(0).textValue()).get("I").textValue();
            O = om.readTree(om.readTree(answer).findValue("fio").get(0).textValue()).get("O").textValue();
        } catch (IOException ex) {
        }
        return F + " " + I + " " + O;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }


    public CloseableHttpResponse getHTTPResponse(String addr, Map<String, String> parameterList) {
        try {
            BasicCookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpUriRequest req;
            CloseableHttpResponse response;
            RequestConfig requestConfig = RequestConfig
                    .copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();
            RequestBuilder reqBuilder = RequestBuilder.post().setUri(new URI(addr));
            for (String key : parameterList.keySet()) {
                reqBuilder.addParameter(key, parameterList.get(key));
            }
            req = reqBuilder.setConfig(requestConfig).build();
            response = httpclient.execute(req);
            return response;
        } catch (URISyntaxException | IOException ex) {
            return null;
        }
    }

}
