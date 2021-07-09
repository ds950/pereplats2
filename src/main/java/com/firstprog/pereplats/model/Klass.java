package com.firstprog.pereplats.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "klass")
public class Klass implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uslovie")
    private String uslovie;

    @Column(name = "statja")
    private String statja;

    protected Klass() {
    }

    public Klass(String uslovie) {
        this.uslovie = uslovie;
    }

    @Override
    public String toString() {
        return String.format("Klass[id=%d, uslovie='%s', statja='%s']", id, uslovie, statja);
    }
}
