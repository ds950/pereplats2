package com.firstprog.pereplats.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PtkPensioner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column
    private String fa;

    @Column
    private String im;

    @Column
    private String ot;

    @Column
    private String rep_up_fio;

    @Column
    private String rdat;

    @Column
    private String rnl;

    @Column
    private String npers;

    @Column
    private String adrfakt;

    @Column
    private Integer ra;

    @Column
    private Integer re;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFa() {
        return fa;
    }

    public void setFa(String fa) {
        this.fa = fa;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getRep_up_fio() {
        return rep_up_fio;
    }

    public void setRep_up_fio(String rep_up_fio) {
        this.rep_up_fio = rep_up_fio;
    }

    public String getRdat() {
        return rdat;
    }

    public void setRdat(String rdat) {
        this.rdat = rdat;
    }

    public String getRnl() {
        return rnl;
    }

    public void setRnl(String rnl) {
        this.rnl = rnl;
    }

    public String getNpers() {
        return npers;
    }

    public void setNpers(String npers) {
        this.npers = npers;
    }

    public String getAdrfakt() {
        return adrfakt;
    }

    public void setAdrfakt(String adrfakt) {
        this.adrfakt = adrfakt;
    }

    public Integer getRa() {
        return ra;
    }

    public void setRa(Integer ra) {
        this.ra = ra;
    }

    public Integer getRe() {
        return re;
    }

    public void setRe(Integer re) {
        this.re = re;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PtkPensioner)) {
            return false;
        }
        PtkPensioner other = (PtkPensioner) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.firstprog.pereplats.model.PtkPensioner[ id=" + id + " ]";
    }

}
