package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonService;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class WordWriter {

    private final MilitaryPersonService militaryPersonService;

    public WordWriter(MilitaryPersonService militaryPersonService) {
        this.militaryPersonService = militaryPersonService;
    }

    public String fillDodatok2(long id, File file) {
        MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonById(id);
        String resultSave = null;
        String templatePath = "docs/templates/dodatok2template.docx";
        String resultsPath = file.getPath();

        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis)) {

            // Замінюємо значення поля "FirstName" на нове значення
            documentFill(document, militaryPerson);
            // Зберігаємо змінений документ
            try (FileOutputStream fos = new FileOutputStream(resultsPath)) {
                document.write(fos);
                resultSave = "Дані успішно збережені: " + resultsPath;
                System.out.println(resultSave);
            } catch (FileNotFoundException e) {
                resultSave = "Помилка під час запису у файл: " + resultsPath;
                System.err.println(resultSave);
            }
            System.out.println("Дані успішно додані до word документу.");
        } catch (IOException e) {
            resultSave = "Помилка відкриття файлу-шаблону: " + templatePath;
            System.err.println(resultSave);
        }

        return resultSave;
    }

    private void documentFill(XWPFDocument document, MilitaryPerson militaryPerson) {
        replaceField(document, "prizv", militaryPerson.getPrepod().getFam());
        replaceField(document, "imya", militaryPerson.getPrepod().getImya());
        replaceField(document, "po_bat", militaryPerson.getPrepod().getOtch());
        replaceField(document, "birth", militaryPerson.getPrepod().getDr().toString());
        replaceField(document, "gromad", "Україна");
        replaceField(document, "osvita", militaryPerson.getEducationLevel());
        fillOsvita(document, militaryPerson);
        fillOsvitaPostgraduate(document, militaryPerson);
        replaceField(document, "misce_roboty", militaryPerson.getPrepod().getKafedra().getKabr());
        replaceField(document, "posada", militaryPerson.getPrepod().getDolghnost().getDolghnName());
        replaceField(document, "cur_data", LocalDate.now().toString());
        replaceField(document, "sim_stan", militaryPerson.getFamilyState());
        fillRoduna(document, militaryPerson);
        fiilAddress(document, militaryPerson);
        fillPasport(document, militaryPerson);

        replaceFieldInTable(document, militaryPerson);
    }

    private void fillOsvita(XWPFDocument document, MilitaryPerson militaryPerson) {
        List<Education> educationsList = militaryPerson.getPrepod().getEducationList().stream().
                sorted(Comparator.comparing(Education::getYearVypusk)).toList();
        fillTable(document, 2, 1, prepareOsvita(educationsList));
        fillTable(document, 2, 6, prepareSpecialnist(educationsList));
    }

    private void fillOsvitaPostgraduate(XWPFDocument document, MilitaryPerson militaryPerson) {
        List<EducationPostgraduate> educationsList = militaryPerson.getPrepod().getEducationPostList().stream().
                sorted(Comparator.comparing(EducationPostgraduate::getYearFinish)).toList();
        setOsvitaLevelCheckBox(document, educationsList);
        if (!educationsList.isEmpty()) {
            fillTable(document, 3, 1, prepareOsvitaPostgaduate(educationsList));
        }
    }

    private void setOsvitaLevelCheckBox(XWPFDocument document, List<EducationPostgraduate> educationsList) {
        String level = "";
        if (!educationsList.isEmpty())
            level = educationsList.get(educationsList.size() - 1).getLevelTraining();
        if ("Аспірантура".equals(level)) {
            replaceField(document, "asp", " X ");
        } else {
            replaceField(document, "asp", "   ");
        }
        if ("Адʼюнктура".equals(level)) {
            replaceField(document, "adj", " X ");
        } else {
            replaceField(document, "adj", "   ");
        }
        if ("Докторантура".equals(level)) {
            replaceField(document, "doct", " X ");
        } else {
            replaceField(document, "doct", "   ");
        }
    }

    private String[][] prepareOsvita(List<Education> educationsList) {
        String[][] osvita = new String[educationsList.size()][3];
        int i = 0;
        for (Education education : educationsList) {
            String[] currentEducation = new String[3];
            currentEducation[0] = education.getVnz().getVnzShortName();
            currentEducation[1] = education.getDiplomaNumber();
            currentEducation[2] = education.getYearVypusk();
            osvita[i] = currentEducation;
            i++;
        }
        return osvita;
    }

    private String[][] prepareSpecialnist(List<Education> educationsList) {
        String[][] spec = new String[educationsList.size()][3];
        int i = 0;
        for (Education education : educationsList) {
            String[] currentEducation = new String[3];
            currentEducation[0] = education.getDiplomaSpeciality();
            currentEducation[1] = education.getDiplomaQualification();
            currentEducation[2] = education.getFormTraining();
            spec[i] = currentEducation;
            i++;
        }
        return spec;
    }

    private String[][] prepareOsvitaPostgaduate(List<EducationPostgraduate> educationsList) {
        String[][] postOsvita = new String[educationsList.size()][4];
        int i = 0;
        for (EducationPostgraduate education : educationsList) {
            String[] currentEducation = new String[4];
            currentEducation[0] = education.getVnz().getVnzShortName();
            currentEducation[1] = "";
            currentEducation[2] = education.getYearFinish();
            currentEducation[3] = education.getLevelTraining();
            postOsvita[i] = currentEducation;
            i++;
        }
        return postOsvita;
    }

    private void fillRoduna(XWPFDocument document, MilitaryPerson militaryPerson) {
        Set<FamilyMember> familyMembers = militaryPerson.getPrepod().getFamily();
        String[][] members = new String[familyMembers.size()][3];
        int i = 0;
        for (FamilyMember member : familyMembers) {
            String[] currentMember = new String[3];
            currentMember[0] = member.getVidRidstva();
            String memberName = String.format("%s %s %s", member.getMemImya(), member.getMemOtch(), member.getMemFam());
            currentMember[1] = memberName;
            currentMember[2] = member.getRikNarodz();
            members[i] = currentMember;
            i++;
        }

        fillTable(document, 4, 1, members);
    }

    private void fillPasport(XWPFDocument document, MilitaryPerson militaryPerson) {
        Set<Document> documents = militaryPerson.getPrepod().getDocuments();
        for (Document pasport : documents) {
            if ("Паперовий паспорт".equals(pasport.getDocType()) || "ID картка".equals(pasport.getDocType())) {
                if ("Паперовий паспорт".equals(pasport.getDocType())) {
                    replaceField(document, "ser", pasport.getDocNumber().trim().substring(0, 2));
                    replaceField(document, "nom", pasport.getDocNumber().trim().substring(2));
                } else {
                    replaceField(document, "ser", "   ");
                    replaceField(document, "nom", pasport.getDocNumber());
                }
                replaceField(document, "vudan", pasport.getKtoVyd());
                replaceField(document, "datvudach", pasport.getDataVyd().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
    }

    private void fillTable(XWPFDocument document, Integer tableNumber, Integer startPosition, String[][] tableData) {
        Integer position = startPosition;
        XWPFTable table = document.getTableArray(tableNumber);
        for (String[] tableDatum : tableData) {
            XWPFTableRow newRow = table.getRow(position);
            if (newRow == null) {
                newRow = table.createRow();
            }
            position++;
            for (int j = 0; j < tableDatum.length; j++) {
                XWPFTableCell cell = newRow.getCell(j);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                XWPFRun run = paragraph.createRun();
                run.setText(tableDatum[j]);
                run.setFontFamily("Times New Roman");
            }
        }
    }

    private void replaceFieldInTable(XWPFDocument document, MilitaryPerson militaryPerson) {
        XWPFTable table = document.getTableArray(5);
        XWPFTableRow row = table.getRow(0);
        replaceFieldinTable(row.getCell(0), "grup_obl", militaryPerson.getVGrupa());
        replaceFieldinTable(row.getCell(0), "kat_obl", militaryPerson.getVCategory().toString());
        replaceFieldinTable(row.getCell(0), "sklad_obl", militaryPerson.getVSklad().getSkladName());
        replaceFieldinTable(row.getCell(0), "zvan_obl", militaryPerson.getVZvanie().getZvanieName());
        replaceFieldinTable(row.getCell(0), "vos_obl", militaryPerson.getVos());
        replaceFieldinTable(row.getCell(1), "pridat_obl", militaryPerson.getVPrydatnist());
        replaceFieldinTable(row.getCell(1), "reest_viisk_obl", militaryPerson.getVoenkomat().getVoenkomatName());
        replaceFieldinTable(row.getCell(1), "act_viisk_obl", militaryPerson.getVoenkomat().getVoenkomatName());
        replaceFieldinTable(row.getCell(1), "spec_obl", militaryPerson.getReserv());
    }

    private void replaceFieldinTable(XWPFTableCell cell, String fieldName, String value) {
        for (XWPFParagraph paragraph : cell.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(fieldName)) {
                    text = text.replace(fieldName, Objects.requireNonNullElse(value, "____"));
                    run.setText(text, 0);
                    run.setUnderline(UnderlinePatterns.SINGLE);
                }
            }
        }
    }

    private void replaceField(XWPFDocument document, String fieldName, String value) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(fieldName)) {
                    text = text.replace(fieldName, Objects.requireNonNullElse(value, ""));
                    run.setText(text, 0);
                    run.setUnderline(UnderlinePatterns.SINGLE);
                }
            }
        }
    }

    private void fiilAddress(XWPFDocument document, MilitaryPerson militaryPerson) {
        StringBuilder rez_addr = new StringBuilder().append(System.lineSeparator());
        if (militaryPerson.getPrepod().getContacts().getPostIndex() != null) {
            rez_addr.append(militaryPerson.getPrepod().getContacts().getPostIndex()).append(", ");
        }
        if (militaryPerson.getPrepod().getContacts().getCountry() != null) {
            rez_addr.append(militaryPerson.getPrepod().getContacts().getCountry());
        }
        if (militaryPerson.getPrepod().getContacts().getOblastUA() != null) {
            rez_addr.append(" ").append(militaryPerson.getPrepod().getContacts().getOblastUA());
        }
        rez_addr.append(" ").append(militaryPerson.getPrepod().getContacts().getCity());
        rez_addr.append(", ").append(militaryPerson.getPrepod().getContacts().getRowAddress()).append(System.lineSeparator());

        StringBuilder facr_addrsb = new StringBuilder().append(System.lineSeparator());
        if (militaryPerson.getPrepod().getContacts().getFactPostIndex() != null) {
            facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactPostIndex()).append(", ");
        }
        if (militaryPerson.getPrepod().getContacts().getFactСountry() != null) {
            facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactСountry());
        }
        if (militaryPerson.getPrepod().getContacts().getFactOblastUA() != null) {
            facr_addrsb.append(" ").append(militaryPerson.getPrepod().getContacts().getFactOblastUA());
        }
        facr_addrsb.append(" ").append(militaryPerson.getPrepod().getContacts().getFactCity());
        facr_addrsb.append(", ").append(militaryPerson.getPrepod().getContacts().getFactRowAddress()).append(System.lineSeparator());
        facr_addrsb.append("Контакти: ").append(militaryPerson.getPrepod().getContacts().getPhoneMain());
        if (militaryPerson.getPrepod().getContacts().getPhoneDop() != null) {
            facr_addrsb.append(" ").append(militaryPerson.getPrepod().getContacts().getPhoneDop());
        }

        replaceField(document, "kur_adr", facr_addrsb.toString());
        replaceField(document, "rez_adr", rez_addr.toString());
    }
}

