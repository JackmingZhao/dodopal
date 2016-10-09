package com.dodopal.oss.delegate.util;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {

    /**
     * 得到excel 表格中的单元格值
     */

    public static String getValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
            // return the pure string value ;
//            DecimalFormat df = new DecimalFormat("0.00");  
            return String.valueOf(cell.getNumericCellValue());  
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }
}
