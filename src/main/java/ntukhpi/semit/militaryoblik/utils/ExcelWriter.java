package ntukhpi.semit.militaryoblik.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ExcelWriter {

    public void writeExcel(String[][] data) {
        String resultsPath = "docs/results/d1_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".xlsx";
        String templatePath = "docs/templates/post1487_2022d5template.xlsx";

        try (FileInputStream fis = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Отримуємо перший аркуш (індекс 0)
            CellStyle wrapStyle = getCellStyle(workbook);

            // Знаходимо останній номер рядка
            int lastRowNum = sheet.getLastRowNum();

            for (String[] tableData : data) {
                Row newRow = sheet.createRow(lastRowNum);
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

            // Зберігаємо змінений документ у файл
            try (FileOutputStream fos = new FileOutputStream(resultsPath)) {
                workbook.write(fos);
            }

            System.out.println("Дані успішно додані до існуючої таблиці.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
