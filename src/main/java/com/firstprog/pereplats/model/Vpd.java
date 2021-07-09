package com.firstprog.pereplats.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vpd")
public class Vpd implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "vpd")
    private String vpd;

    @Column(name = "vpdskl")
    private String vpdskl;

    protected Vpd() {
    }

    public Vpd(String vpd, String vpdskl) {

        this.vpd = vpd;
        this.vpdskl = vpdskl;
    }

    public String getVpd() {
        return vpd;
    }

    public void setVpd(String vpd) {
        this.vpd = vpd;
    }

    public String getVpdskl() {
        return vpdskl;
    }

    public void setVpdskl(String vpdskl) {
        this.vpdskl = vpdskl;
    }

    @Override
    public String toString() {
        return String.format("Vpd[id=%d, vpd='%s', vpdskl='%s']", id, vpd, vpdskl);
    }
}
