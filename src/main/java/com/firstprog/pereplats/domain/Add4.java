package com.firstprog.pereplats.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Add4 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String upr;

    @Column(name = "vip_his_month")
    private String viphismonth;

    private String vip_summ;

    private String nach_summ;

    private String vip_razn;

    private String fio_search;

    private Double result_final;

    private Integer pensid;

    public Add4(String upr, String viphismonth, String vip_summ, String nach_summ, String vip_razn, Double result_final, Integer pensid) {
        this.upr = upr;
        this.viphismonth = viphismonth;
        this.vip_summ = vip_summ;
        this.nach_summ = nach_summ;
        this.vip_razn = vip_razn;
        this.result_final = result_final;
        this.pensid = pensid;

    }

    public Add4(){}

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

    public String getViphismonth() throws ParseException {

        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = oldDateFormat.parse(viphismonth);

        return newDateFormat.format(date);
    }

    public void setViphismonth(String viphismonth) {
        this.viphismonth = viphismonth;
    }

    public String getVip_summ() {
        return vip_summ;
    }

    public void setVip_summ(String vip_summ) {
        this.vip_summ = vip_summ;
    }

    public String getNach_summ() {
        return nach_summ;
    }

    public void setNach_summ(String nach_summ) {
        this.nach_summ = nach_summ;
    }

    public String getVip_razn() {
        return vip_razn;
    }

    public void setVip_razn(String vip_razn) {
        this.vip_razn = vip_razn;
    }

    public Double getResult_final() {
        return result_final;
    }

    public void setResult_final(Double result_final) {
        this.result_final = result_final;
    }

    public Integer getPensid() {
        return pensid;
    }

    public void setPensid(Integer pensid) {
        this.pensid = pensid;
    }

    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s','%s','%s','%s']", upr, viphismonth, vip_summ, nach_summ, vip_razn, result_final, pensid);
    }
}