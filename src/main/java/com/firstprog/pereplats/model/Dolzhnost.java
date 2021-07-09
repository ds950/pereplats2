package com.firstprog.pereplats.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dolzhnost")
public class Dolzhnost implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dolzhnost")
    private String dolzhnost;

    protected Dolzhnost() {
    }

    public Dolzhnost(String dolzhnost) {
        this.dolzhnost = dolzhnost;
    }

    @Override
    public String toString() {
        return String.format("Vp[id=%d, dolzhnost='%s']", id, dolzhnost);
    }
}