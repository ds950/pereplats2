package com.firstprog.pereplats.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pensioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fio;

    private String fam;

    private String im;

    private String otch;

    private String snl;

    private String nvd;

    private String vp;

    private String vpd;

    private String calendar;

    private String adress;

    private String uprcode;

    private String pensnomer;

    public Pensioner(String fio, String fam, String im, String otch, String snl, String nvd, String vp, String vpd, String calendar, String adress, String uprcode, String pensnomer) {
        this.fio = fio;
        this.fam = fam;
        this.im = im;
        this.otch = otch;
        this.snl = snl;
        this.nvd = nvd;
        this.vp = vp;
        this.vpd = vpd;
        this.calendar = calendar;
        this.adress = adress;
        this.uprcode = uprcode;
        this.pensnomer = pensnomer;
    }

    public Pensioner() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFam() {
        return fam;
    }

    public void setFam(String fam) {
        this.fam = fam;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getOtch() {
        return otch;
    }

    public void setOtch(String otch) {
        this.otch = otch;
    }

    public String getSnl() {
        return snl;
    }

    public void setSnl(String snl) {
        this.snl = snl;
    }

    public String getNvd() {
        return nvd;
    }

    public void setNvd(String nvd) {
        this.nvd = nvd;
    }

    public String getVp() {
        return vp;
    }

    public void setVp(String vp) {
        this.vp = vp;
    }

    public String getVpd() {
        return vpd;
    }

    public void setVpd(String vpd) {
        this.vpd = vpd;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUprcode() {
        return uprcode;
    }

    public void setUprcode(String uprcode) {
        this.uprcode = uprcode;
    }

    public String getPensnomer() {
        return pensnomer;
    }

    public void setPensnomer(String pensnomer) {
        this.pensnomer = pensnomer;
    }


    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s']",fio, fam, im, otch, snl, nvd, vp, vpd, calendar, adress, uprcode, pensnomer);
    }
}
