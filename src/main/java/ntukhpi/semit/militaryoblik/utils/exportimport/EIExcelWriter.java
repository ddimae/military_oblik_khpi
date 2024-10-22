package ntukhpi.semit.militaryoblik.utils.exportimport;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class EIExcelWriter {
    public String writeExcel(List<String[]> data, File file) {
        String resultSave = null;
        String resultsPath = file.getPath();
        String templatePath = EISettings.TEMPLATE_PATH;

        try (FileInputStream fis = new FileInputStream(templatePath);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(EISettings.GLOBAL_SHEET_INDEX);
            int rowCount = EISettings.GLOBAL_START_ROW;

            for (int i = 0; i < data.size(); i++) {
                for (String tableData : data.get(i)) {
                    Row row = sheet.getRow(rowCount);
                    Cell cell = row.createCell(EISettings.GLOBAL_EXPORT_COLUMN);

                    cell.setCellValue(tableData);
                    rowCount++;
                }
            }

            // Зберігаємо змінений документ у файл
            try (FileOutputStream fos = new FileOutputStream(resultsPath)) {
                workbook.write(fos);
                resultSave = "Дані успішно збережені: "+resultsPath;
                System.out.println(resultSave);
            } catch (FileNotFoundException e) {
                resultSave = "Помилка під час запису у файл: "+resultsPath;
                System.err.println(resultSave);
            }
        } catch (IOException e) {
            resultSave = "Помилка відкриття файлу-шаблону: "+templatePath;
            System.err.println(resultSave);
        }
        return resultSave;
    }
}
