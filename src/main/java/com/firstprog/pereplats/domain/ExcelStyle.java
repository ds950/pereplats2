package com.firstprog.pereplats.domain;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;

public class ExcelStyle {
    public static void  rowStyle0_6(Row row, HSSFCellStyle style1){
        row.getCell(0).setCellStyle(style1);
        row.getCell(1).setCellStyle(style1);
        row.getCell(2).setCellStyle(style1);
        row.getCell(3).setCellStyle(style1);
        row.getCell(4).setCellStyle(style1);
        row.getCell(5).setCellStyle(style1);
        row.getCell(6).setCellStyle(style1);
    }

    public static void  rowStyle0_12(Row row, HSSFCellStyle style1){
        rowStyle0_6(row, style1);
        row.getCell(7).setCellStyle(style1);
        row.getCell(8).setCellStyle(style1);
        row.getCell(9).setCellStyle(style1);
        row.getCell(10).setCellStyle(style1);
        row.getCell(11).setCellStyle(style1);
        row.getCell(12).setCellStyle(style1);
    }

    public static void rowStyle0_11(Row row, HSSFCellStyle style1){
        row.getCell(0).setCellStyle(style1);
        row.getCell(1).setCellStyle(style1);
        row.getCell(2).setCellStyle(style1);
        row.getCell(3).setCellStyle(style1);
        row.getCell(4).setCellStyle(style1);
        row.getCell(5).setCellStyle(style1);
        row.getCell(6).setCellStyle(style1);
        row.getCell(7).setCellStyle(style1);
        row.getCell(8).setCellStyle(style1);
        row.getCell(9).setCellStyle(style1);
        row.getCell(10).setCellStyle(style1);
        row.getCell(11).setCellStyle(style1);
    }
}
