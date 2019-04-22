package com.alliedtesting;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class ReadText {
    public static Object[][] readFile(File file) throws IOException {
        String text = FileUtils.readFileToString(file);
        String[] row = text.split("!");

        int rowNum = row.length;
        int colNum = row[0].split(";").length;

        Object[][] data = new String[rowNum][colNum];

        for (int i = 0; i < rowNum; i++) {
            String[] cols = row[i].split(";");
            for (int j = 0; j < colNum; j++) {
                data[i][j] = cols[j].trim();
//                System.out.println("The value is " + cols[j].trim());
            }
        }
        return data;
    }
}
