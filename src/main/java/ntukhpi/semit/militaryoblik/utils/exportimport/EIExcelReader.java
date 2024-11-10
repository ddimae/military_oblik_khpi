package ntukhpi.semit.militaryoblik.utils.exportimport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class EIExcelReader {
    public String[] readExcel(File file) {
        List<String> importData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(EISettings.GLOBAL_SHEET_INDEX);
            int maxRowNum = EISettings.GLOBAL_GENERAL_DATA_COUNT +
                            EISettings.GLOBAL_CONTACTS_DATA_COUNT +
                            EISettings.GLOBAL_EDUCATION_DATA_COUNT +
                            EISettings.GLOBAL_POSTEDUCATION_DATA_COUNT +
                            EISettings.GLOBAL_FAMILY_DATA_COUNT +
                            EISettings.GLOBAL_DOCUMENTS_DATA_COUNT;

            for (int i = EISettings.GLOBAL_IMPORT_ROW; i <= maxRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(EISettings.GLOBAL_EXPORT_COLUMN);

                importData.add(cell.getStringCellValue());
            }
        } catch (IOException e) {
            System.err.println("Помилка відкриття файлу: " + file.getName());
        }

        return importData.toArray(new String[0]);
    }
}
