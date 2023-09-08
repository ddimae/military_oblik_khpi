package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.CurrentDoljnostInfo;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.MilitaryPersonService;
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
    public static List<D05Adapter> reservistList = new ArrayList<>();
    private static final String  fileDodatok5Name = "docs/input/dodatok5_3TCK_WORK.xlsx";

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
            System.out.println(sheet.getFirstRowNum()+" "+sheet.getLastRowNum());
            for (Row row : sheet) {
                Prepod prepod = new Prepod();
                //Комірка 1 - номер пп
                //Комірка 3 - ПІБ
                String cellText = row.getCell(2).getStringCellValue();
                String[] nameParts = cellText.split(" ");
                prepod.setFam(nameParts[0]);
                prepod.setImya(nameParts[1]);
                if (nameParts.length>3) {
                    prepod.setOtch(nameParts[2]+" "+nameParts[3]);
                } else {
                    prepod.setOtch(nameParts[2]);
                }
                //Комірка 4 - Дата рождения
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                java.util.Date d =  row.getCell(3).getDateCellValue();
                LocalDate localDate = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
                prepod.setDr(localDate);
                //Пошук препода в БД
                Prepod prepInDB = prepodServiceImpl.getPrepodByExapmleWithDr(prepod);
                if (prepInDB==null) {
                    System.err.println("Data about prep "+prepod.getFam()+" absent!" );
                    continue;
                }
                System.out.println(prepInDB);
                //if was found get values other cells

                //Військова інформація
                MilitaryPerson militaryPerson = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepInDB);
                if (militaryPerson==null) {
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
                System.out.println(militaryPerson);


                //Документи
                //Комірка 10 - Паспорт
                cellText = row.getCell(9).getStringCellValue();
                Document doc = parseDok(cellText);
                if (doc!=null)
                System.out.println(doc);
                else System.err.println("Дані паспорту відсутні або некоректні");

                //Работа
                //Комірка 17 - Наказ про работу
                cellText = row.getCell(16).getStringCellValue();
                System.out.println("Місце роботи - "+cellText);
                CurrentDoljnostInfo work = parseNakaz(cellText);
                if (work!=null)
                    System.out.println(work);
                else System.err.println("Дані про наказ відсутні");
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            pDok.setDocNumber(matcher.group(1)+matcher.group(2));
            pDok.setKtoVyd(matcher.group(3));
            pDok.setDataVyd(LocalDate.parse(matcher.group(4),DateTimeFormatter.ofPattern("dd.MM.yyyy")));
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
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
            work = new CurrentDoljnostInfo();
            //Присвоить сотрудника!!!
            work.setNumNakazStart(matcher.group(1));
            work.setDateStart(LocalDate.parse(matcher.group(2),DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        return work;

    }
}
