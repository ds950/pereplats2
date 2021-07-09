package com.firstprog.pereplats.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "vp")
public class Vp implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "vp")
    private String vp;

    protected Vp() {
    }

    public Vp(String vp) {
        this.vp = vp;
    }

    @Override
    public String toString() {
        return String.format("Vp[id=%d, vp='%s']", id, vp);
    }
}
