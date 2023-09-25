package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VZvanieServiceImpl;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Dodatok5Reader {

    //private

    public static List<D05Adapter> reservistList = new ArrayList<>();
    private static final String fileDodatok5Name = "docs/input/dodatok5_3TCK_WORK.xlsx";

    @Autowired
    private VZvanieServiceImpl vZvanieServiceImpl;

    @Autowired
    private PrepodServiceImpl prepodServiceImpl;
    @Autowired
    private MilitaryPersonServiceImpl militaryPersonServiceImpl;

    public Dodatok5Reader(VZvanieServiceImpl vZvanieServiceImpl,
                          PrepodServiceImpl prepodServiceImpl,
                          MilitaryPersonServiceImpl militaryPersonServiceImpl) {
        this.vZvanieServiceImpl = vZvanieServiceImpl;
        this.prepodServiceImpl = prepodServiceImpl;
        this.militaryPersonServiceImpl = militaryPersonServiceImpl;
    }

    public void readExcelFileWithDodatok5() {
        Path path = Paths.get(fileDodatok5Name); //отримуємо шлях до файлу

        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(2); // отримуємо перший аркуш
            System.out.println(sheet.getFirstRowNum() + " " + sheet.getLastRowNum());
            for (Row row : sheet) {
                Prepod prepInDB = null;
                Prepod prepod = new Prepod();
                //Комірка 1 - номер пп
                //Комірка 3 - ПІБ
                String cellText = row.getCell(2).getStringCellValue();
                String[] nameParts = cellText.split(" ");
                prepod.setFam(nameParts[0]);
                prepod.setImya(nameParts[1]);
                if (nameParts.length > 3) {
                    prepod.setOtch(nameParts[2] + " " + nameParts[3]);
                } else {
                    prepod.setOtch(nameParts[2]);
                }
                //Комірка 4 - Дата рождения
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                java.util.Date d = row.getCell(3).getDateCellValue();
                LocalDate localDate = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
                prepod.setDr(localDate);
                //Пошук препода в БД
                prepInDB = prepodServiceImpl.getPrepodByExapmleWithDr(prepod);
                if (prepInDB == null) {
                    System.err.println("Data about prep " + prepod.getFam() + " absent!");
                    continue;
                }
                //System.out.println(prepInDB);
                //if was found get values other cells

                //Військова інформація
                MilitaryPerson militaryPerson = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepInDB);
                if (militaryPerson == null) {
                    militaryPerson = new MilitaryPerson();
                    militaryPerson.setPrepod(prepInDB);
                    //Комірка 2 військове звання - приводим ко всем маленьким
                    String vzvanieName = row.getCell(1).getStringCellValue().toLowerCase();
//                System.out.println(vzvanieName);
                    //Комірка 6 ВОС
                    String vos = row.getCell(5).getStringCellValue();
//                System.out.println(vos);
                    //Комірка 7 Состав
                    String sklad = row.getCell(6).getStringCellValue();
//                System.out.println(sklad);
                    //Комірка 8 Категорія обліку (2 , sometime 1)
                    String category = String.valueOf((int) row.getCell(7).getNumericCellValue());
//                System.out.println(category);
                    //Комірка 13 Военкомат
                    String voenkomatName = row.getCell(12).getStringCellValue();
//                System.out.println(voenkomatName);
                    //Комірка 14 Спец облік
                    String specOblik = row.getCell(13).getStringCellValue();
//                System.out.println(specOblik);
                    //Комірка 15 Придатність
                    String goden = row.getCell(14).getStringCellValue();
//                System.out.println(goden);

                    militaryPerson = militaryPersonServiceImpl.
                            saveMilitaryInfo(prepInDB, voenkomatName, vzvanieName, sklad,
                                    vos, Integer.parseInt(category), "військовозобов\'язаний",
                                    goden, "немає");
                }

                //Документи
                //Комірка 10 - Паспорт
                cellText = row.getCell(9).getStringCellValue();
                Document doc = parseDok(cellText);
                if (doc != null)
                    //System.out.println(doc);
                    prepInDB.addDocument(doc);
                else System.err.println("Дані паспорту відсутні або некоректні");

                //Работа
                //Комірка 17 - Наказ про работу
                cellText = row.getCell(16).getStringCellValue();
//                System.out.println("Місце роботи - " + cellText);
                CurrentDoljnostInfo work = parseNakaz(cellText);
                if (work != null)
                    // System.out.println(work);
                    prepInDB.setPosadaNakazy(work);
                else System.err.println("Дані про наказ відсутні");

                //Склад родини
                //Комірка 16 - Состав семьи
                cellText = row.getCell(15).getStringCellValue();
//                System.out.println("Склад родини - " + cellText);
                String[] familyMembersStr = cellText.split(";");
                //контролировать в ячейках наявність тексту типу "одружений", "неодружений", "розлучений" і ";"
                //Відомості про членів родини вказувати, додаючи ";"
                String familyStat = familyMembersStr[0];
                militaryPerson.setFamilyState(familyStat);
                prepInDB.delFamily();
                //Set<FamilyMember> relatives = new LinkedHashSet<>();
                //якщо нічого більше немає, то все
                if (familyMembersStr.length > 1) {
                    for (int i = 1; i < familyMembersStr.length; i++) {
                        FamilyMember familyMember = parseFamilyMember(prepInDB, familyMembersStr[i]);
                        //relatives.add(familyMember);
                        prepInDB.addMember(familyMember);
                    }
                }

                //Oсвіта
                //Комірка 9 - Освіта
                cellText = row.getCell(8).getStringCellValue();
//                System.out.println("Освіта - " + cellText);
                String[] osvitaBloks = cellText.split(";");
                //контролировать в ячейках наявність тексту типу "вища", "повна вища", "вища спеціальна" і ";"
                //Відомості про закінчення внз вказувати, додаючи ";"
                String osvitaLevel = osvitaBloks[0];
                militaryPerson.setEdicationLevel(osvitaLevel);
//                System.out.println(osvitaLevel);
                prepInDB.delDocuments();
//                Set<Education> eduList = new LinkedHashSet<>();
                //якщо нічого більше немає, то все
                if (osvitaBloks.length > 1) {
                    for (int i = 1; i < osvitaBloks.length; i++) {
                        Education eduItem = parseEducation(prepInDB, osvitaBloks[i]);
                        if (eduItem != null) {
                            //Ще треба витягувати дані по внз і додавати, якщо не в базі
//                            eduList.add(eduItem);
                            prepInDB.addEducation(eduItem);
                        }
                    }
                }

                System.out.println(militaryPerson);
                System.out.println("======================================================");

            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String regex_rik = "\\s*у\\s*(\\d{4})\\s*р.";

    //Регулярка дещо не зрозуміла:
    //Нащо пробіл - для врахування ситуації ЛС ВЕ (є такий). Обовязково робити трим, що прибрати останній пробіл в серії
    //Нащо / в номері - знайшовся із номером 401/11
    private static final String regex_num_diploma = "([A-ЯA-Z0-9 ]+)*\\s*№([0-9\\/]+)";

    public Education parseEducation(Prepod prepInDB, String osvitaBlok) {
        Education edu = null;
        String eduString = osvitaBlok.trim();
//        System.out.println("start string - " + eduString);
        int pozDviKrapky = eduString.indexOf(":");
        //На початку має бути спец або бак або маг або мсп та двокрапка після нього;
        if (pozDviKrapky > 0) {
            //якщо знайдено ":" продовжуємо
            String kodOsvitaLevel = osvitaBlok.substring(0, pozDviKrapky);
            String levelName = levelOsvita(kodOsvitaLevel);
//            System.out.println("level - " + levelName);
            //Якщо забули немає одного з кодів, закінчення
            if (levelName.length() > 0) {
                //Знайдений
                eduString = eduString.substring(pozDviKrapky + 1).trim();
                String[] eduParts = eduString.split(",");
                //Отримуємо дані по внз - він має бути обовязково
                String eduName = eduParts[0];
                int pozLBr = eduName.indexOf("(");
                int pozRBr = eduName.indexOf(")");
                String vnzShortName = "";
                String vnzFullName = "";
                //Відшукується рік закінчення у вигляді " у 2012 р."
                Pattern pattern = Pattern.compile(regex_rik);
                Matcher matcher = pattern.matcher(eduName);
                String vnzEndRik = "";
                int fisrtSymRik = eduName.length();
                if (matcher.find()) {
                    vnzEndRik = matcher.group(1);
                    fisrtSymRik = matcher.start();
                }
                if (pozRBr > pozLBr) {
                    //тобто есть полна назва в дужках
                    vnzFullName = eduName.substring(pozLBr + 1, pozRBr);
                    vnzShortName = eduName.substring(0, pozLBr - 1);
                } else {
                    //нема повної назви -1 -1
                    if (vnzEndRik.length() == 0) {
                        vnzShortName = eduName.trim();
                    } else {
                        vnzShortName = eduName.substring(0, fisrtSymRik).trim();
                    }
                }
                VNZaklad vnz = new VNZaklad();
                vnz.setId(0L);
                vnz.setVnzShortName(vnzShortName);
                vnz.setVnzName(vnzFullName);
//                System.out.println(vnzShortName);
//                System.out.println(vnzFullName);
//                System.out.println(vnzEndRik);
                String seriaDipl = "";
                String numDipl = "";
                String fax = "";
                String kval = "";
                //Далі може не бути окремих складових
                //Але - якщо на початку "фах:", далі спеціальність підготовки
                // якщо "кв:" - кваліфікація, нічого з переліченого - це серія і номер диплома
                // !!! Файл треба готувати - розставити де треба фах, кв та №
                for (int i = 1; i < eduParts.length; i++) {
                    String part = eduParts[i].trim();
                    if (part.startsWith("фах:")) {
                        fax = part.substring(4).trim();
                    } else {
                        if (part.startsWith("кв:")) {
                            kval = part.substring(3).trim();
                        } else {
                            pattern = Pattern.compile(regex_num_diploma);
                            matcher = pattern.matcher(part);
                            if (matcher.find()) {
                                seriaDipl = matcher.group(1) != null ? matcher.group(1).trim() : "";
                                numDipl = matcher.group(2).trim();
                            }
                        }
                    }
                }
//                System.out.println(seriaDipl);
//                System.out.println(numDipl);
//                System.out.println(fax);
//                System.out.println(kval);
                edu = new Education();
                edu.setId(0L);
                edu.setPrepod(prepInDB);
                edu.setVnz(vnz);
                edu.setDiplomaSeries(seriaDipl);
                edu.setDiplomaNumber(numDipl);
                edu.setDiplomaSpeciality(fax);
                edu.setDiplomaQualification(kval);
                edu.setLevelTraining(levelName);
                edu.setYearVypusk(vnzEndRik);
            }
        }
        return edu;
    }

    private String levelOsvita(String levelKod) {
        String level = "";
        switch (levelKod) {
            case "спец":
                level = "спеціаліст";
                break;
            case "маг":
                level = "магістр";
                break;
            case "бак":
                level = "бакалавр";
                break;
            case "мсп":
                level = "мол.спеціаліст";
                break;
        }
        return level;
    }

    private static final String regex_pasport = "([А-Я]{2})\\s*№*(\\d{6})\\s*,\\s*([А-Яа-яЇїІіЄєҐґ\\s].*)(\\d{2}\\.\\d{2}\\.\\d{4})";
    private static final String regex_idcard = "№*(\\d{9})\\s*,\\s*(\\d{4})*\\s*,\\s*(\\d{2}\\.\\d{2}\\.\\d{4})";

    private Document parseDok(String cellValue) {
        Document pDok = null;

        final String string = cellValue.trim();

        Pattern pattern = Pattern.compile(regex_pasport);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
            pDok = new Document();
            //Присвоить сотрудника!!!
            pDok.setDocType("Паперовий паспорт");
            pDok.setDocNumber(matcher.group(1) + matcher.group(2));
            pDok.setKtoVyd(matcher.group(3));
            pDok.setDataVyd(LocalDate.parse(matcher.group(4), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            pattern = Pattern.compile(regex_idcard);
            matcher = pattern.matcher(string);
            if (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
                pDok = new Document();
                //Присвоить сотрудника!!!
                pDok.setDocType("ID CARD");
                pDok.setDocNumber(matcher.group(1));
                pDok.setKtoVyd(matcher.group(2));
                pDok.setDataVyd(LocalDate.parse(matcher.group(3), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
        return pDok;

    }

    private static final String regex_work = "наказ\\s*№(\\d+)\\s*від\\s*(\\d{2}\\.\\d{2}\\.\\d{4})";

    private CurrentDoljnostInfo parseNakaz(String cellValue) {
        CurrentDoljnostInfo work = null;

        final String string = cellValue.trim();

        Pattern pattern = Pattern.compile(regex_work);
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
            work = new CurrentDoljnostInfo();
            //Присвоить сотрудника!!!
            work.setNumNakazStart(matcher.group(1));
            work.setDateStart(LocalDate.parse(matcher.group(2), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        return work;

    }

    public FamilyMember parseFamilyMember(Prepod prep, String familyMemberString) {
        FamilyMember res = null;
        familyMemberString = familyMemberString.trim();
        //Найти ' - ', слева будет название члена семьи
        int PozTire = familyMemberString.indexOf(" - ");
        String memberName = familyMemberString.substring(0, PozTire);
        //System.out.println(memberName);
        if (isMemberNameInList(memberName)) {
            familyMemberString = familyMemberString.substring(PozTire + 3).trim();
            int pozComa = familyMemberString.indexOf(", ");
            String fam = "";
            String imya = "";
            String otch = "";
            int year = 0;
            String[] nameParts;
            if (pozComa != -1) {
                year = Integer.parseInt(familyMemberString.substring(pozComa + 2, pozComa + 6));
                familyMemberString = familyMemberString.substring(0, pozComa);
                nameParts = familyMemberString.trim().split(" ");

                if (nameParts.length == 3) {
                    fam = nameParts[0];
                    imya = nameParts[1];
                    otch = nameParts[2];
                } else {
                    if (nameParts.length == 2) {
                        imya = nameParts[0];
                        otch = nameParts[1];
                    } else {
                        if (nameParts.length == 2) {
                            imya = nameParts[0];
                        }
                    }
                }
            } else {
                //не введене імя родича - тільки рік народження або імя без року
                char first = familyMemberString.charAt(0);
                if (Character.isDigit(first)) {
                    //тільки рік народження
                    familyMemberString = familyMemberString.trim();
                    year = Integer.parseInt(familyMemberString.substring(0, 4));
                } else {
                    nameParts = familyMemberString.trim().split(" ");

                    if (nameParts.length == 3) {
                        fam = nameParts[0];
                        imya = nameParts[1];
                        otch = nameParts[2];
                    } else {
                        if (nameParts.length == 2) {
                            imya = nameParts[0];
                            otch = nameParts[1];
                        } else {
                            if (nameParts.length == 2) {
                                imya = nameParts[0];
                            }
                        }
                    }
                }

            }
            String yearStr = year > 0 ? String.valueOf(year) : "";
            res = new FamilyMember(prep, fam, imya, otch, memberName, yearStr);
        }
        return res;
    }

    private boolean isMemberNameInList(String memberName) {
        String[] listMember =
                new String[]{
                        "дружина",
                        "дочка", "донька",
                        "син",
                        "дочка дружини", "донька дружини",
                        "син дружини",
                        "чоловік",
                        "дочка чоловіка", "донька чоловіка",
                        "син чоловіка"
                };
        List<String> okMemberList = new ArrayList<>(List.of(listMember));
        return okMemberList.contains(memberName);
    }

    private static final String regex_sity_address = "((^м\\.?|м\\.?|смт) ?[А-Яа-яїіє ]+),([0-9А-Яа-яЇїІіЄєҐґ \\.\\,\\/]+)";

    public PersonalData parsePersonalDate(Prepod prep, String address1, String address2) {
        PersonalData pd = null;
        int comaPos1 = -1;
        String addr1 = "";
        String obl1 = "";
        if (address1.contains("обл.")) {
            comaPos1 = address1.indexOf(",");
            obl1 = address1.substring(0, comaPos1);
        }
        addr1 = address1.substring(comaPos1 + 1);

        Pattern pattern = Pattern.compile(regex_sity_address);
        Matcher matcher = pattern.matcher(addr1);

        if (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            System.out.println("city - "+matcher.group(1).trim());
            System.out.println("addr - "+matcher.group(3).trim());
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
        }

        return pd;
    }

}
