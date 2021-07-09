package com.firstprog.pereplats.domain;

import javax.persistence.*;

@Entity
public class Upr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String upr;

    private String upr_full;

    private String upr_nach;

    private String upr_adress;

    @Column(name = "upr_code")
    private String uprcode;

    public Upr(String upr, String upr_full, String upr_nach, String upr_adress, String uprcode) {

        this.upr = upr;
        this.upr_full = upr_full;
        this.upr_nach = upr_nach;
        this.upr_adress = upr_adress;
        this.uprcode = uprcode;
    }

    public Upr() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpr() {
        return upr;
    }

    public void setUpr(String upr) {
        this.upr = upr;
    }

    public String getUpr_full() {
        return upr_full;
    }

    public void setUpr_full(String upr_full) {
        this.upr_full = upr_full;
    }

    public String getUpr_nach() {
        return upr_nach;
    }

    public void setUpr_nach(String upr_nach) {
        this.upr_nach = upr_nach;
    }

    public String getUpr_adress() {
        return upr_adress;
    }

    public void setUpr_adress(String upr_adress) {
        this.upr_adress = upr_adress;
    }

    public String getUprcode() {
        return uprcode;
    }

    public void setUprcode(String uprcode) {
        this.uprcode = uprcode;
    }


    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s']", upr, upr_full, upr_nach, upr_adress, uprcode);
    }
}
