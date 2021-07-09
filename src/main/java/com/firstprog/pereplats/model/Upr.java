package com.firstprog.pereplats.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "upr")
public class Upr implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "upr")
    private String upr;

    @Column(name = "upr_full")
    private String upr_full;

    @Column(name = "upr_nach")
    private String upr_nach;

    @Column(name = "upr_adress")
    private String upr_adress;

    @Column(name = "upr_code")
    private String uprcode;

    protected Upr() {
    }

    public Upr(String upr) {
        this.upr = upr;
    }

    @Override
    public String toString() {
        return String.format("Upr[id=%d, upr='%s', upr_full='$s', upr_nach='$s', upr_adress='$s',]", id, upr, upr_full, upr_nach, upr_adress, uprcode);
    }
}
