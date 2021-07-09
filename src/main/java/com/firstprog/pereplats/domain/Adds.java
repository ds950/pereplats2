package com.firstprog.pereplats.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Adds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uprcode;

    private String add1exist;

    private Integer pensid;

    private String add1reshdate;

    private String upr;

    private String add1opismistake;

    private String add1statja;

    private String add1mistake;

    private String add1viptype;

    private String add1viprazn;

    private String add2exist;

    private String add2raspnadp;

    private String add2rukdolzh;

    private String add2rukfio;

    private String add2protdate;

    private String uprfull;

    private String add2vv;

    private String add2date1;

    private String add2date2;

    private String add2summrub;

    private String add2mistakeauthor;

    private String add2prich;

    private String add2provdolzh;

    private String add2proizvdolzh;

    private String add2provfio;

    private String add2proizvfio;

    private String add5exist;

    private String upradress;

    private String tel;

    private String uprnach;

    private String uprisp;

    private String add3exist;

    private String add3date;

    private String blocks;

    private String block1fio;

    private String block1num;

    private String block1date;

    private String block1snl;

    private String block1vd;

    private String block2fio;

    private String block2snl;

    private String block2vd;

    private String klass;

    private String block3date;

    private String block3razmer;

    private String block3razmertverd;

    private String add6exist;

    private String add9exist;

    private String add9raspnadp;

    private String add9rukdolzh;

    private String add9rukfio;

    private String add9protdate;

    private String add9vv;

    private String add9date1;

    private String add9date2;

    private String add9summrub;

    private String add9mistakeauthor;

    private String add9prich;

    private String add9provdolzh;

    private String add9proizvdolzh;

    private String add9provfio;

    private String add9proizvfio;

    public Adds(String uprcode, String add1exist, Integer pensid, String add1reshdate, String upr, String add1opismistake,
                String add1statja, String add1mistake, String add1viptype, String add1viprazn, String add2exist,
                String add2raspnadp, String add2rukdolzh, String add2rukfio, String add2protdate, String uprfull,
                String add2vv, String add2date1, String add2date2, String add2summrub, String add2mistakeauthor,
                String add2prich, String add2provdolzh, String add2proizvdolzh, String add2provfio, String add2proizvfio,
                String add5exist, String upradress, String tel, String uprnach, String uprisp, String add3exist,
                String add3date, String blocks, String block1fio, String block1num, String block1date, String block1snl,
                String block1vd, String block2fio, String block2snl, String block2vd, String klass, String block3date,
                String block3razmer, String block3razmertverd, String add6exist, String add9exist, String add9raspnadp,
                String add9rukdolzh, String add9rukfio, String add9protdate, String add9vv, String add9date1,
                String add9date2, String add9summrub, String add9mistakeauthor, String add9prich, String add9provdolzh,
                String add9proizvdolzh, String add9provfio, String add9proizvfio) {
        this.uprcode = uprcode;
        this.add1exist = add1exist;
        this.pensid = pensid;
        this.add1reshdate = add1reshdate;
        this.upr = upr;
        this.add1opismistake = add1opismistake;
        this.add1statja = add1statja;
        this.add1mistake = add1mistake;
        this.add1viptype = add1viptype;
        this.add1viprazn = add1viprazn;
        this.add2exist = add2exist;
        this.add2raspnadp = add2raspnadp;
        this.add2rukdolzh = add2rukdolzh;
        this.add2rukfio = add2rukfio;
        this.add2protdate = add2protdate;
        this.uprfull = uprfull;
        this.add2vv = add2vv;
        this.add2date1 = add2date1;
        this.add2date2 = add2date2;
        this.add2summrub = add2summrub;
        this.add2mistakeauthor = add2mistakeauthor;
        this.add2prich = add2prich;
        this.add2provdolzh = add2provdolzh;
        this.add2proizvdolzh = add2proizvdolzh;
        this.add2provfio = add2provfio;
        this.add2proizvfio = add2proizvfio;
        this.add5exist = add5exist;
        this.upradress = upradress;
        this.tel = tel;
        this.uprnach = uprnach;
        this.uprisp = uprisp;
        this.add3exist = add3exist;
        this.add3date = add3date;
        this.blocks = blocks;
        this.block1fio = block1fio;
        this.block1num = block1num;
        this.block1date = block1date;
        this.block1snl = block1snl;
        this.block1vd = block1vd;
        this.block2fio = block2fio;
        this.block2snl = block2snl;
        this.block2vd = block2vd;
        this.klass = klass;
        this.block3date = block3date;
        this.block3razmer = block3razmer;
        this.block3razmertverd = block3razmertverd;
        this.add6exist = add6exist;
        this.add9exist = add9exist;
        this.add9raspnadp = add9raspnadp;
        this.add9rukdolzh = add9rukdolzh;
        this.add9rukfio = add9rukfio;
        this.add9protdate = add9protdate;
        this.add9vv = add9vv;
        this.add9date1 = add9date1;
        this.add9date2 = add9date2;
        this.add9summrub = add9summrub;
        this.add9mistakeauthor = add9mistakeauthor;
        this.add9prich = add9prich;
        this.add9provdolzh = add9provdolzh;
        this.add9proizvdolzh = add9proizvdolzh;
        this.add9provfio = add9provfio;
        this.add9proizvfio = add9proizvfio;
    }

    public Adds() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUprcode() {
        return uprcode;
    }

    public void setUprcode(String uprcode) {
        this.uprcode = uprcode;
    }

    public String getAdd1exist() {
        return add1exist;
    }

    public void setAdd1exist(String add1exist) {
        this.add1exist = add1exist;
    }

    public Integer getPensid() {
        return pensid;
    }

    public void setPensid(Integer pensid) {
        this.pensid = pensid;
    }

    public String getAdd1reshdate() {
        return add1reshdate;
    }

    public void setAdd1reshdate(String add1reshdate) {
        this.add1reshdate = add1reshdate;
    }

    public String getUpr() {
        return upr;
    }

    public void setUpr(String upr) {
        this.upr = upr;
    }

    public String getAdd1opismistake() {
        return add1opismistake;
    }

    public void setAdd1opismistake(String add1opismistake) {
        this.add1opismistake = add1opismistake;
    }

    public String getAdd1statja() {
        return add1statja;
    }

    public void setAdd1statja(String add1statja) {
        this.add1statja = add1statja;
    }

    public String getAdd1mistake() {
        return add1mistake;
    }

    public void setAdd1mistake(String add1mistake) {
        this.add1mistake = add1mistake;
    }

    public String getAdd1viptype() {
        return add1viptype;
    }

    public void setAdd1viptype(String add1viptype) {
        this.add1viptype = add1viptype;
    }

    public String getAdd1viprazn() {
        return add1viprazn;
    }

    public void setAdd1viprazn(String add1viprazn) {
        this.add1viprazn = add1viprazn;
    }

    public String getAdd2exist() {
        return add2exist;
    }

    public void setAdd2exist(String add2exist) {
        this.add2exist = add2exist;
    }

    public String getAdd2raspnadp() {
        return add2raspnadp;
    }

    public void setAdd2raspnadp(String add2raspnadp) {
        this.add2raspnadp = add2raspnadp;
    }

    public String getAdd2rukdolzh() {
        return add2rukdolzh;
    }

    public void setAdd2rukdolzh(String add2rukdolzh) {
        this.add2rukdolzh = add2rukdolzh;
    }

    public String getAdd2rukfio() {
        return add2rukfio;
    }

    public void setAdd2rukfio(String add2rukfio) {
        this.add2rukfio = add2rukfio;
    }

    public String getAdd2protdate() {
        return add2protdate;
    }

    public void setAdd2protdate(String add2protdate) {
        this.add2protdate = add2protdate;
    }

    public String getUprfull() {
        return uprfull;
    }

    public void setUprfull(String uprfull) {
        this.uprfull = uprfull;
    }

    public String getAdd2vv() {
        return add2vv;
    }

    public void setAdd2vv(String add2vv) {
        this.add2vv = add2vv;
    }

    public String getAdd2date1() {
        return add2date1;
    }

    public void setAdd2date1(String add2date1) {
        this.add2date1 = add2date1;
    }

    public String getAdd2date2() {
        return add2date2;
    }

    public void setAdd2date2(String add2date2) {
        this.add2date2 = add2date2;
    }

    public String getAdd2summrub() {
        return add2summrub;
    }

    public void setAdd2summrub(String add2summrub) {
        this.add2summrub = add2summrub;
    }

    public String getAdd2mistakeauthor() {
        return add2mistakeauthor;
    }

    public void setAdd2mistakeauthor(String add2mistakeauthor) {
        this.add2mistakeauthor = add2mistakeauthor;
    }

    public String getAdd2prich() {
        return add2prich;
    }

    public void setAdd2prich(String add2prich) {
        this.add2prich = add2prich;
    }

    public String getAdd2provdolzh() {
        return add2provdolzh;
    }

    public void setAdd2provdolzh(String add2provdolzh) {
        this.add2provdolzh = add2provdolzh;
    }

    public String getAdd2proizvdolzh() {
        return add2proizvdolzh;
    }

    public void setAdd2proizvdolzh(String add2proizvdolzh) {
        this.add2proizvdolzh = add2proizvdolzh;
    }

    public String getAdd2provfio() {
        return add2provfio;
    }

    public void setAdd2provfio(String add2provfio) {
        this.add2provfio = add2provfio;
    }

    public String getAdd2proizvfio() {
        return add2proizvfio;
    }

    public void setAdd2proizvfio(String add2proizvfio) {
        this.add2proizvfio = add2proizvfio;
    }

    public String getAdd5exist() {
        return add5exist;
    }

    public void setAdd5exist(String add5exist) {
        this.add5exist = add5exist;
    }

    public String getUpradress() {
        return upradress;
    }

    public void setUpradress(String upradress) {
        this.upradress = upradress;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUprnach() {
        return uprnach;
    }

    public void setUprnach(String uprnach) {
        this.uprnach = uprnach;
    }

    public String getUprisp() {
        return uprisp;
    }

    public void setUprisp(String uprisp) {
        this.uprisp = uprisp;
    }

    public String getAdd3exist() {
        return add3exist;
    }

    public void setAdd3exist(String add3exist) {
        this.add3exist = add3exist;
    }

    public String getAdd3date() {
        return add3date;
    }

    public void setAdd3date(String add3date) {
        this.add3date = add3date;
    }

    public String getBlocks() {
        return blocks;
    }

    public void setBlocks(String blocks) {
        this.blocks = blocks;
    }

    public String getBlock1fio() {
        return block1fio;
    }

    public void setBlock1fio(String block1fio) {
        this.block1fio = block1fio;
    }

    public String getBlock1num() {
        return block1num;
    }

    public void setBlock1num(String block1num) {
        this.block1num = block1num;
    }

    public String getBlock1date() {
        return block1date;
    }

    public void setBlock1date(String block1date) {
        this.block1date = block1date;
    }

    public String getBlock1snl() {
        return block1snl;
    }

    public void setBlock1snl(String block1snl) {
        this.block1snl = block1snl;
    }

    public String getBlock1vd() {
        return block1vd;
    }

    public void setBlock1vd(String block1vd) {
        this.block1vd = block1vd;
    }

    public String getBlock2fio() {
        return block2fio;
    }

    public void setBlock2fio(String block2fio) {
        this.block2fio = block2fio;
    }

    public String getBlock2snl() {
        return block2snl;
    }

    public void setBlock2snl(String block2snl) {
        this.block2snl = block2snl;
    }

    public String getBlock2vd() {
        return block2vd;
    }

    public void setBlock2vd(String block2vd) {
        this.block2vd = block2vd;
    }

    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public String getBlock3date() {
        return block3date;
    }

    public void setBlock3date(String block3date) {
        this.block3date = block3date;
    }

    public String getBlock3razmer() {
        return block3razmer;
    }

    public void setBlock3razmer(String block3razmer) {
        this.block3razmer = block3razmer;
    }

    public String getBlock3razmertverd() {
        return block3razmertverd;
    }

    public void setBlock3razmertverd(String block3razmertverd) {
        this.block3razmertverd = block3razmertverd;
    }

    public String getAdd6exist() {
        return add6exist;
    }

    public void setAdd6exist(String add6exist) {
        this.add6exist = add6exist;
    }

    public String getAdd9exist() {
        return add9exist;
    }

    public void setAdd9exist(String add9exist) {
        this.add9exist = add9exist;
    }

    public String getAdd9raspnadp() {
        return add9raspnadp;
    }

    public void setAdd9raspnadp(String add9raspnadp) {
        this.add9raspnadp = add9raspnadp;
    }

    public String getAdd9rukdolzh() {
        return add9rukdolzh;
    }

    public void setAdd9rukdolzh(String add9rukdolzh) {
        this.add9rukdolzh = add9rukdolzh;
    }

    public String getAdd9rukfio() {
        return add9rukfio;
    }

    public void setAdd9rukfio(String add9rukfio) {
        this.add9rukfio = add9rukfio;
    }

    public String getAdd9protdate() {
        return add9protdate;
    }

    public void setAdd9protdate(String add9protdate) {
        this.add9protdate = add9protdate;
    }

    public String getAdd9vv() {
        return add9vv;
    }

    public void setAdd9vv(String add9vv) {
        this.add9vv = add9vv;
    }

    public String getAdd9date1() {
        return add9date1;
    }

    public void setAdd9date1(String add9date1) {
        this.add9date1 = add9date1;
    }

    public String getAdd9date2() {
        return add9date2;
    }

    public void setAdd9date2(String add9date2) {
        this.add9date2 = add9date2;
    }

    public String getAdd9summrub() {
        return add9summrub;
    }

    public void setAdd9summrub(String add9summrub) {
        this.add9summrub = add9summrub;
    }

    public String getAdd9mistakeauthor() {
        return add9mistakeauthor;
    }

    public void setAdd9mistakeauthor(String add9mistakeauthor) {
        this.add9mistakeauthor = add9mistakeauthor;
    }

    public String getAdd9prich() {
        return add9prich;
    }

    public void setAdd9prich(String add9prich) {
        this.add9prich = add9prich;
    }

    public String getAdd9provdolzh() {
        return add9provdolzh;
    }

    public void setAdd9provdolzh(String add9provdolzh) {
        this.add9provdolzh = add9provdolzh;
    }

    public String getAdd9proizvdolzh() {
        return add9proizvdolzh;
    }

    public void setAdd9proizvdolzh(String add9proizvdolzh) {
        this.add9proizvdolzh = add9proizvdolzh;
    }

    public String getAdd9provfio() {
        return add9provfio;
    }

    public void setAdd9provfio(String add9provfio) {
        this.add9provfio = add9provfio;
    }

    public String getAdd9proizvfio() {
        return add9proizvfio;
    }

    public void setAdd9proizvfio(String add9proizvfio) {
        this.add9proizvfio = add9proizvfio;
    }

    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s','%s','%s','%s','%s'," +
                "'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'," +
                "'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'," +
                "'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'," +
                        "'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s']",
                uprcode, add1exist, pensid, add1reshdate, upr, add1opismistake, add1statja,
                add1mistake, add1viptype, add1viprazn, add2exist, add2raspnadp, add2rukdolzh,
                add2rukfio, add2protdate,  uprfull, add2vv, add2date1, add2date2,
                add2summrub, add2mistakeauthor, add2prich, add2provdolzh,
                add2proizvdolzh, add2provfio, add2proizvfio, add5exist, upradress, tel, uprnach,
                uprisp, add3exist, add3date, blocks, block1fio, block1num,
                block1date, block1snl, block1vd, block2fio, block2snl, block2vd,
                klass, block3date, block3razmer, block3razmertverd, add6exist,
                add9exist, add9raspnadp, add9rukdolzh,
                add9rukfio, add9protdate, add9vv, add9date1, add9date2,
                add9summrub, add9mistakeauthor, add9prich,
                add9provdolzh, add9proizvdolzh, add9provfio, add9proizvfio);
    }

}

