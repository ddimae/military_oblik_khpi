package ntukhpi.semit.militaryoblik.utils.exportimport;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.util.List;

@Component
public class EIExcelWriter {
    public String writeExcelPersonData(List<String[]> personData, File file) {
        String resultSave = null;
        String resultsPath = file.getPath();
        String templatePath = EISettings.TEMPLATE_PATH;

        try (FileInputStream fis = new FileInputStream(templatePath);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(EISettings.GLOBAL_SHEET_INDEX);
            int rowCount = EISettings.GLOBAL_START_ROW;

            for (int i = 0; i < personData.size(); i++) {
                for (String tableData : personData.get(i)) {
                    Row row = sheet.getRow(rowCount);
                    Cell cell = row.createCell(EISettings.GLOBAL_EXPORT_COLUMN);

                    cell.setCellValue(tableData);
                    rowCount++;
                }
            }

            copyExcelTables(workbook,
                    EISettings.GLOBAL_SHEET_INDEX,
                    new Point(EISettings.EDUCATION_GLOBAL_TABLE_START_COLUMN, EISettings.EDUCATION_GLOBAL_TABLE_START_ROW),
                    new Dimension(EISettings.EDUCATION_COL_COUNT, EISettings.MAX_EDUCATION_NUMBER),
                    EISettings.EDUCATION_SHEET_INDEX,
                    new Point(EISettings.EDUCATION_DEST_TABLE_START_COLUMN, EISettings.EDUCATION_DEST_TABLE_START_ROW));

            copyExcelTables(workbook,
                    EISettings.GLOBAL_SHEET_INDEX,
                    new Point(EISettings.POSTEDUCATION_GLOBAL_TABLE_START_COLUMN, EISettings.POSTEDUCATION_GLOBAL_TABLE_START_ROW),
                    new Dimension(EISettings.POSTEDUCATION_COL_COUNT, EISettings.MAX_POSTEDUCATION_NUMBER),
                    EISettings.POSTEDUCATION_SHEET_INDEX,
                    new Point(EISettings.POSTEDUCATION_DEST_TABLE_START_COLUMN, EISettings.POSTEDUCATION_DEST_TABLE_START_ROW));

            copyExcelTables(workbook,
                    EISettings.GLOBAL_SHEET_INDEX,
                    new Point(EISettings.FAMILY_GLOBAL_TABLE_START_COLUMN, EISettings.FAMILY_GLOBAL_TABLE_START_ROW),
                    new Dimension(EISettings.FAMILY_COL_COUNT, EISettings.MAX_FAMILY_NUMBER),
                    EISettings.FAMILY_SHEET_INDEX,
                    new Point(EISettings.FAMILY_DEST_TABLE_START_COLUMN, EISettings.FAMILY_DEST_TABLE_START_ROW));

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

    public String writeExcelDropdownListData(List<String[]> dropdownListData, File file) {
        return null;
    }

    private void copyExcelTables(Workbook workbook, int sourceSheetIndex, Point sourceTopLeftCoords, Dimension sourceWidthHeight,
                                 int destSheetIndex, Point destTopLeftCoords) {
        Sheet sourceSheet = workbook.getSheetAt(sourceSheetIndex);
        Sheet destSheet = workbook.getSheetAt(destSheetIndex);
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

        for (int sI = sourceTopLeftCoords.y, dI = destTopLeftCoords.y; sI < sourceTopLeftCoords.y + sourceWidthHeight.height; sI++, dI++) {
            Row sourceRow = sourceSheet.getRow(sI);
            Row destRow = destSheet.getRow(dI);

            for (int sJ = sourceTopLeftCoords.x, dJ = destTopLeftCoords.x; sJ < sourceTopLeftCoords.x + sourceWidthHeight.width; sJ++, dJ++) {
                Cell sourceCell = sourceRow.getCell(sJ);
                Cell destCell = destRow.getCell(dJ);
                CellValue sourceCellValue = evaluator.evaluate(sourceCell);

                destCell.setCellValue(sourceCellValue.getStringValue());
//                System.out.println(evaluator.evaluate(sourceCell).getStringValue() + " | " + destCell.getStringCellValue());
            }
        }
    }
}
