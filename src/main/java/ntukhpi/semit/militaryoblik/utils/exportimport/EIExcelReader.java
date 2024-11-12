package ntukhpi.semit.militaryoblik.utils.exportimport;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class EIExcelReader {
    private String[] readExcel(File file, boolean readImportData) {
        List<String> importData = new ArrayList<>();

        int readColumn = readImportData ? EISettings.GLOBAL_IMPORT_COLUMN : EISettings.GLOBAL_EXPORT_COLUMN;

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            Sheet sheet = workbook.getSheetAt(EISettings.GLOBAL_SHEET_INDEX);
            int maxRowNum = EISettings.GLOBAL_GENERAL_DATA_COUNT +
                            EISettings.GLOBAL_CONTACTS_DATA_COUNT +
                            EISettings.GLOBAL_EDUCATION_DATA_COUNT +
                            EISettings.GLOBAL_POSTEDUCATION_DATA_COUNT +
                            EISettings.GLOBAL_FAMILY_DATA_COUNT +
                            EISettings.GLOBAL_DOCUMENTS_DATA_COUNT;

            for (int i = EISettings.GLOBAL_IMPORT_ROW; i <= maxRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(readColumn);

                String cellValue = "";

                evaluator.evaluateInCell(cell);
                switch (cell.getCellType()) {
//                    case FORMULA:
//                        CellValue formulaResult = evaluator.evaluate(cell);
//                        switch (formulaResult.getCellType()) {
//                            case NUMERIC:
//                                cellValue = String.valueOf(formulaResult.getNumberValue());
//                                break;
//                            case STRING:
//                                cellValue = formulaResult.getStringValue();
//                                break;
//                        }
//                        break;
                    case NUMERIC:
                        cellValue = String.valueOf((long)cell.getNumericCellValue());
                        break;
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    default:
                        break;
                }
                System.out.println(cell.getCellType());
                importData.add(cellValue);
            }
        } catch (IOException e) {
            System.err.println("Помилка відкриття файлу: " + file.getName());
        }

        return importData.toArray(new String[0]);
    }

    public String[] readImportExcel(File file) {
        return readExcel(file, true);
    }

    public String[] readExportExcel(File file) {
        return readExcel(file, false);
    }
}
