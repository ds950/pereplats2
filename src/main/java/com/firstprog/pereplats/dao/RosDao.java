package com.firstprog.pereplats.dao;


import com.firstprog.pereplats.model.PtkPensioner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.util.List;

public class RosDao extends JdbcDaoSupport {

    //private static final int[] TYPEPROCESSES = {536, 649, 647};
    private final RowMapper<PtkPensioner> ptkpensRowMapper = (ResultSet rs, int i) -> {
        PtkPensioner ptkPensioner = new PtkPensioner();
        ptkPensioner.setId(rs.getString("ID"));
        ptkPensioner.setFa(rs.getString("FA"));
        ptkPensioner.setIm(rs.getString("IM"));
        ptkPensioner.setOt(rs.getString("OT"));
        ptkPensioner.setRep_up_fio(rs.getString("REP_UP_FIO"));
        ptkPensioner.setRdat(rs.getString("RDAT"));
        ptkPensioner.setRnl(rs.getString("RNL"));
        ptkPensioner.setNpers(rs.getString("NPERS"));
        ptkPensioner.setAdrfakt(rs.getString("ADRFAKT"));
        ptkPensioner.setRe(rs.getInt("RE"));
        ptkPensioner.setRa(rs.getInt("RA"));
        return ptkPensioner;
    };


    public List<PtkPensioner> findPtkByName(String rep_up_fio) {
        String sql = "SELECT ID, RE, RA, REP_UP_FIO, RDAT, RNL, NPERS, ADRFAKT FROM PF.MAN WHERE REP_UP_FIO LIKE ? AND RE=41 AND RA=18";
        return getJdbcTemplate().query(sql, ptkpensRowMapper, rep_up_fio);

    }

    public List<PtkPensioner> findPtkBySnils(String npers) {
        String sql = "SELECT ID, FA, IM, OT, RE, RA, REP_UP_FIO, RDAT, RNL, NPERS, ADRFAKT FROM PF.MAN WHERE NPERS LIKE ?";
        return getJdbcTemplate().query(sql, ptkpensRowMapper, npers);

    }


}
