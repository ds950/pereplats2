package com.firstprog.pereplats.domain;

import javax.persistence.*;

@Entity
@Table(name = "adminparam")
public class Adminparam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kolpopitok")
    private Long kolpopitok;

    @Column(name = "koefpopitok")
    private Long koefpopitok;

    @Column(name = "block")
    private Long block;


    public Adminparam() {
    }

    public Adminparam(Long kolpopitok, Long koefpopitok, Long block) {
        id = 1l;
        this.kolpopitok = kolpopitok;
        this.koefpopitok = koefpopitok;
        this.block = block;
    }

    public Long getId() {
        return id;
    }

    public Long getBlock() {
        return block;
    }

    public void setBlock(Long block) {
        this.block = block;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKolpopitok() {
        return kolpopitok;
    }

    public void setKolpopitok(Long kolpopitok) {
        this.kolpopitok = kolpopitok;
    }

    public Long getKoefpopitok() {
        return koefpopitok;
    }

    public void setKoefpopitok(Long koefpopitok) {
        this.koefpopitok = koefpopitok;
    }
}