package ntukhpi.semit.militaryoblik.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelWriter {

    public void writeExcel(List<String[][]> data, File file) {
        String resultsPath = file.getPath();
        String templatePath = "docs/templates/post1487_2022d5template.xlsx";

        try (FileInputStream fis = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            createSheets(data, workbook);

            for (int i = 0; i < data.size(); i++) {
                Sheet sheet = workbook.getSheetAt(i); // Отримуємо вказаний аркуш
                CellStyle wrapStyle = getCellStyle(workbook);

                // Знаходимо останній номер рядка
                int lastRowNum = sheet.getLastRowNum();
                int num = 1;
                for (String[] tableData : data.get(i)) {
                    Row newRow = sheet.createRow(lastRowNum);
                    tableData[0] = Integer.toString(num);
                    num++;
                    for (int cellNum = 0; cellNum < tableData.length; cellNum++) {
                        Cell cell = newRow.createCell(cellNum);
                        if (tableData[cellNum] != null) {
                            cell.setCellValue(tableData[cellNum]);
                        } else {
                            cell.setCellValue("");
                        }
                        cell.setCellStyle(wrapStyle);
                    }
                    lastRowNum = lastRowNum + 1;
                }
            }

            // Зберігаємо змінений документ у файл
            try (FileOutputStream fos = new FileOutputStream(resultsPath)) {
                workbook.write(fos);
            }

            System.out.println("Дані успішно додані до існуючої таблиці.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //створення додаткових листів
    private void createSheets(List<String[][]> data, Workbook workbook) {
        for (int i = 0; i < data.size(); i++) {
            if (i == 0) {
                workbook.setSheetName(i, data.get(i)[0][12]);
            } else {
                workbook.cloneSheet(0);
                workbook.setSheetName(i, data.get(i)[0][12]);
            }
        }
    }

    //створення комірок
    private CellStyle getCellStyle(Workbook workbook) {
        CellStyle wrapStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");
        wrapStyle.setWrapText(true);
        wrapStyle.setBorderBottom(BorderStyle.THIN);
        wrapStyle.setBorderLeft(BorderStyle.THIN);
        wrapStyle.setBorderRight(BorderStyle.THIN);
        wrapStyle.setBorderTop(BorderStyle.THIN);
        wrapStyle.setAlignment(HorizontalAlignment.CENTER);
        wrapStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        wrapStyle.setFont(font);
        return wrapStyle;
    }
}
