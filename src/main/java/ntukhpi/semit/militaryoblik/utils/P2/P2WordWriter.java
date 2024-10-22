package ntukhpi.semit.militaryoblik.utils.P2;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonService;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class P2WordWriter {

    private final MilitaryPersonService militaryPersonService;

    public P2WordWriter(MilitaryPersonService militaryPersonService) {
        this.militaryPersonService = militaryPersonService;
    }

    public String fillFormP2(long id, File file) {
        MilitaryPerson militaryPerson = militaryPersonService.getMilitaryPersonById(id);
        String resultSave = null;
        String templatePath = "docs/templates/post1487_2022Fp_2template.docx";
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
//            System.out.println("Дані успішно додані до word документу.");
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
        String drString = militaryPerson.getPrepod().getDr() != null ?
                militaryPerson.getPrepod().getDr().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                : "";
        replaceField(document, "birth", drString);
        replaceField(document, "gromad", "Україна");
        replaceField(document, "osvita", militaryPerson.getEducationLevel());
        fillOsvita(document, militaryPerson);
        fillOsvitaPostgraduate(document, militaryPerson);
        replaceField(document, "misce_roboty", militaryPerson.getPrepod().getKafedra().getKabr());
        replaceField(document, "posada", militaryPerson.getPrepod().getDolghnost().getDolghnName());
        //TODO п7 (= метка "cur_data") передбачає вирахування стажу на посаді.
        //Зробити можна, але тоді треба передбачити десь ДАТУ ПОЧАТКУ НАРАХУВАННЯ СТАЖУ,
        //Або зберігати відомості про стаж від початку призначення на посаду
        //Проблема - де брати таку інфорамацію. ТОМУ .... просто пусте значення!!!
//        replaceField(document, "cur_data", LocalDate.now().toString());
        replaceField(document, "cur_data", "              ");
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
        //DD - убрал одну строку из шаблона
        fillTable(document, 2, 5, prepareSpecialnist(educationsList));
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
        if (!educationsList.isEmpty()) {
                if (checkPostGraduatePresence("Аспірантура",educationsList)) {
                    replaceField(document, "asp", " X ");
                } else {
                    replaceField(document, "asp", "   ");
                }
                if (checkPostGraduatePresence("Адʼюнктура",educationsList)) {
                    replaceField(document, "adj", " X ");
                } else {
                    replaceField(document, "adj", "   ");
                }
                if (checkPostGraduatePresence("Докторантура",educationsList)) {
                    replaceField(document, "doct", " X ");
                } else {
                    replaceField(document, "doct", "   ");
                }
        } else {
            replaceField(document, "asp", "   ");
            replaceField(document, "adj", "   ");
            replaceField(document, "doct", "   ");
        }

    }

    private boolean checkPostGraduatePresence(String levelCheck, List<EducationPostgraduate> educationsList) {
        boolean flYes = false;
        for (EducationPostgraduate ep: educationsList) {
            if (levelCheck.equals(ep.getLevelTraining())) {
                return true;
            }
        }
        return flYes;
    }

    private String[][] prepareOsvita(List<Education> educationsList) {
        String[][] osvita = new String[educationsList.size()][3];
        int i = 0;
        for (Education education : educationsList) {
            String[] currentEducation = new String[3];
            currentEducation[0] = education.getVnz().getVnzShortName();
            //Серію та номер треба!!!!
            StringBuilder eduLine = new StringBuilder();
            if (education.getDiplomaSeries() != null)
                eduLine.append(education.getDiplomaSeries());
            if (education.getDiplomaSeries().length() != 0) {
                eduLine.append(" ");
            }
            eduLine.append("№").append(education.getDiplomaNumber());
            currentEducation[1] = eduLine.toString();
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
        List<FamilyMember> familyMembers = militaryPerson.getPrepod().getFamily().stream().
                sorted(Comparator.comparing(FamilyMember::getRikNarodz)).toList();
        String[][] members = new String[familyMembers.size()][3];
        int i = 0;
        for (FamilyMember member : familyMembers) {
            String[] currentMember = new String[3];
            currentMember[0] = member.getVidRidstva();
            String memberName = String.format("%s %s %s", member.getMemFam(), member.getMemImya(), member.getMemOtch());
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
                    //Треба також заповнювати, бо висвітлюється назва мітки
                    replaceField(document, "pasp_ser", "ID-card");
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
        // TODO Треба уточнити, як заповнювати
        replaceFieldinTable(row.getCell(1), "act_viisk_obl", "\n                                   ");
//        replaceFieldinTable(row.getCell(1), "act_viisk_obl", militaryPerson.getVoenkomat().getVoenkomatName());
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
//      Адреса регістрації
        StringBuilder rez_addr = new StringBuilder(System.lineSeparator());
        if (militaryPerson.getPrepod().getContacts().getPostIndex() != null) {
            rez_addr.append(militaryPerson.getPrepod().getContacts().getPostIndex()).append(", ");
        }
        if (militaryPerson.getPrepod().getContacts().getCountry() != null) {
            rez_addr.append(militaryPerson.getPrepod().getContacts().getCountry());
        }
        if (militaryPerson.getPrepod().getContacts().getOblastUA() != null) {
            if (militaryPerson.getPrepod().getContacts().getCountry() != null)
                rez_addr.append(",");
            rez_addr.append(militaryPerson.getPrepod().getContacts().getOblastUA()).append(" обл.");
        }
        if (militaryPerson.getPrepod().getContacts().getOblastUA() != null ||
                (militaryPerson.getPrepod().getContacts().getOblastUA() == null &&
                        militaryPerson.getPrepod().getContacts().getCountry() != null)) {
            rez_addr.append(",");
        }
        rez_addr.append(militaryPerson.getPrepod().getContacts().getCity());
        rez_addr.append(", ").append(militaryPerson.getPrepod().getContacts().getRowAddress()).append(".");
//   Адреса мешкання
        StringBuilder facr_addrsb = new StringBuilder().append(System.lineSeparator());
        if (militaryPerson.getPrepod().getContacts().getFactPostIndex() != null) {
            facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactPostIndex()).append(", ");
        }
        if (militaryPerson.getPrepod().getContacts().getFactСountry() != null) {
            facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactСountry());
        }
        if (militaryPerson.getPrepod().getContacts().getFactOblastUA() != null) {
            if (militaryPerson.getPrepod().getContacts().getFactСountry() != null) {
                facr_addrsb.append(",");
            }
            facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactOblastUA()).append(" обл.");
        }
        if (militaryPerson.getPrepod().getContacts().getFactOblastUA() != null ||
                (militaryPerson.getPrepod().getContacts().getFactOblastUA() == null &&
                        militaryPerson.getPrepod().getContacts().getFactСountry() != null)) {
            facr_addrsb.append(",");
        }
        facr_addrsb.append(militaryPerson.getPrepod().getContacts().getFactCity());
        facr_addrsb.append(", ").append(militaryPerson.getPrepod().getContacts().getFactRowAddress()).append(" ");
        facr_addrsb.append(System.lineSeparator()).append("Контакти: ").append(militaryPerson.getPrepod().getContacts().getPhoneMain());
        if (militaryPerson.getPrepod().getContacts().getPhoneDop() != null) {
            facr_addrsb.append(";").append(militaryPerson.getPrepod().getContacts().getPhoneDop());
        }

        replaceField(document, "kur_adr", facr_addrsb.toString());
        replaceField(document, "rez_adr", rez_addr.toString());
    }
}

