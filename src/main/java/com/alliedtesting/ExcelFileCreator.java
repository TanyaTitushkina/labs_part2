package com.alliedtesting;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileCreator {

    static Workbook originalWb = null;

    public static void workBookCreator(String pageTitle, String url, String wordsNumber, int sheetIndex)
            throws Exception {
        Workbook wb = originalWb;

        if (wb == null) {
            System.out.println("\nThis Excel workbook has not been created, yet.\nWill create it.\n");

            wb = new HSSFWorkbook();
            originalWb = wb;
        }
        Sheet sheet = wb.createSheet();
        wb.setSheetName(sheetIndex, pageTitle);

        List<String> valueNames = new ArrayList();

        valueNames.add("Page url: " + url);
        valueNames.add("Number of Woodpeckers: " + wordsNumber);

        for (int i = 0; i < valueNames.size(); i++) {
            Row row = sheet.createRow(i);
            createCell(wb, row, 0, valueNames.get(i));
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("All_about_woodpeckers.xls");

        wb.write(fileOut);
        fileOut.close();
        System.out.println("All_about_woodpeckers.xls is updated\n");
    }

    private static void createCell(Workbook wb, Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cell.setCellStyle(cellStyle);
    }
}
