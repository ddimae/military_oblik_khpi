package ntukhpi.semit.militaryoblik.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class WordWriter {

    public void readAndWriteFile() {
        String[][] tableData = {
                {"1", "ПІБ1", "посада1", "дата1", "телефон1;ПІБ1@khpi.edu.ua;Харків, Науки пр-кт, 43/45, кв. 134"},
                {"2", "ПІБ2", "посада2", "дата2", "телефон2;ПІБ2@khpi.edu.ua;Харків, Героїв Харкова пр-кт, 270, кв. 34"},
                {"3", "ПІБ3", "посада3", "дата3", "телефон3;ПІБ3@khpi.edu.ua;Харків, 23 серпня вул, 8, кв. 17"},
                {"4", "ПІБ4", "посада4", "дата4", "телефон4;ПІБ4@khpi.edu.ua;Харків, Героїв Труда вул, 68, кв. 65"}};

        String templatePath = "docs/templates/post1487_2022d1template.docx";
//        String templatePath = "src/main/resources/test.docx";

        String resultsPath = "docs/results/d1_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".docx";
//        String resultsPath = "src/main/resources/output.docx";

        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Замінюємо значення поля "FirstName" на нове значення
            replaceField(document, "ter_center", "текст з назвою територіального центра");
            replaceField(document, "samovriad", "текст з назвою органу місцевого самоврядування");
            //заповнюємо таблицю данними
            fillTable(document, tableData);
            // Зберігаємо змінений документ
            try (FileOutputStream fos = new FileOutputStream(resultsPath)) {
                document.write(fos);
            }
//            System.out.println("Дані успішно додані до word документу.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void fillTable(XWPFDocument document, String[][] tableData) {
        XWPFTable table = document.getTableArray(0);
        for (String[] tableDatum : tableData) {
            XWPFTableRow newRow = table.createRow();
            for (int j = 0; j < tableDatum.length; j++) {
                XWPFTableCell cell = newRow.getCell(j);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                run.setText(tableDatum[j]);
                run.setFontFamily("Times New Roman");
            }
        }
    }

    private void replaceField(XWPFDocument document, String fieldName, String value) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(fieldName)) {
                    text = text.replace(fieldName, value);
                    run.setText(text, 0);
                    run.setUnderline(UnderlinePatterns.SINGLE);
                }
            }
        }
    }
}

