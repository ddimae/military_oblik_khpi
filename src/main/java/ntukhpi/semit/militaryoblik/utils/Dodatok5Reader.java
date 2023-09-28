package ntukhpi.semit.militaryoblik.utils;

import ntukhpi.semit.militaryoblik.adapters.D05Adapter;
import ntukhpi.semit.militaryoblik.entity.*;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Country;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.RegionUkraine;
import ntukhpi.semit.militaryoblik.service.*;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Dodatok5Reader {

    //private

    public static List<D05Adapter> reservistList = new ArrayList<>();
    private static final String fileDodatok5Name = "docs/input/dodatok5_3TCK_WORK.xlsx";


    @Autowired
    private PrepodServiceImpl prepodServiceImpl;
    @Autowired
    private MilitaryPersonServiceImpl militaryPersonServiceImpl;
    @Autowired
    private CountryServiceImpl countryServiceImpl;
    @Autowired
    private RegionUkraineServiceImpl regionUkraineServiceImpl;

    @Autowired
    private VZvanieServiceImpl vzvanieServiceImpl;
    @Autowired
    private VoenkomatServiceImpl voenkomatServiceImpl;
    @Autowired
    private VSkladServiceImpl vskladServiceImpl;

    @Autowired
    private CurrentDoljnostInfoServiceImpl currentDoljnostInfoServiceImpl;
    @Autowired
    private PersonalDataServiceImpl personalDataServiceImpl;

    @Autowired
    private DocumentServiceImpl documentServiceImpl;
    @Autowired
    private FamilyMemberServiceImpl familyMemberServiceImpl;
    @Autowired
    private EducationServiceImpl educationServiceImpl;

    @Autowired
    private VNZakladServiceImpl vnzServiceImpl;


    public Dodatok5Reader(
            PrepodServiceImpl prepodServiceImpl
            , MilitaryPersonServiceImpl militaryPersonServiceImpl
            , CountryServiceImpl countryServiceImpl
            , RegionUkraineServiceImpl regionUkraineServiceImpl
            , VZvanieServiceImpl vzvanieServiceImpl
            , VSkladServiceImpl vskladServiceImpl
            , VoenkomatServiceImpl voenkomatServiceImpl
            , CurrentDoljnostInfoServiceImpl currentDoljnostInfoService
            , PersonalDataServiceImpl personalDataServiceImpl
            , DocumentServiceImpl documentServiceImpl
            , FamilyMemberServiceImpl familyMemberServiceImpl
            , EducationServiceImpl educationServiceImpl
            , VNZakladServiceImpl vnzServiceImpl
    ) {
        this.prepodServiceImpl = prepodServiceImpl;
        this.militaryPersonServiceImpl = militaryPersonServiceImpl;
        this.countryServiceImpl = countryServiceImpl;
        this.regionUkraineServiceImpl = regionUkraineServiceImpl;
        this.voenkomatServiceImpl = voenkomatServiceImpl;
        this.vskladServiceImpl = vskladServiceImpl;
        this.vzvanieServiceImpl = vzvanieServiceImpl;
        this.currentDoljnostInfoServiceImpl = currentDoljnostInfoService;
        this.personalDataServiceImpl = personalDataServiceImpl;
        this.documentServiceImpl = documentServiceImpl;
        this.familyMemberServiceImpl = familyMemberServiceImpl;
        this.educationServiceImpl = educationServiceImpl;
        this.vnzServiceImpl = vnzServiceImpl;
    }

    public void readExcelFileWithDodatok5() {
        Path path = Paths.get(fileDodatok5Name); //отримуємо шлях до файлу

        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            Workbook workbook = new XSSFWorkbook(inputStream);
//=========================================================
//00=======================================================
            Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш
            System.out.println(sheet.getFirstRowNum() + " " + sheet.getLastRowNum());
            for (Row row : sheet) {
                Prepod prepInDB = null;
                Prepod prepod = new Prepod();
                //Комірка 1 - номер пп
                //Комірка 3 - ПІБ
                String cellText = row.getCell(2).getStringCellValue();
                String[] nameParts = cellText.split(" ");
                String famExcel = "" + nameParts[0].charAt(0) + nameParts[0].substring(1).toLowerCase();
                prepod.setFam(famExcel);
                prepod.setImya(nameParts[1]);
                if (nameParts.length > 3) {
                    prepod.setOtch(nameParts[2] + " " + nameParts[3]);
                } else {
                    prepod.setOtch(nameParts[2]);
                }
                //Пошук препода в БД
//                prepInDB = prepodServiceImpl.getPrepodByExapmleWithDr(prepod);
                prepInDB = prepodServiceImpl.getPrepodByExapmleFIO(prepod);
                if (prepInDB == null) {
                    System.err.println("=======================================================");
                    System.err.println("Data about prep " + prepod.getFam() + " absent!");
                    System.out.println("FULL INFO: ");
                    System.out.println(prepod.getFam() + " " + prepod.getImya() + " " + prepod.getOtch()
                            + " " + prepod.getDr().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    //Комірка 17 - Наказ про работу
                    cellText = row.getCell(16).getStringCellValue();
                    System.out.println("Місце роботи - " + cellText);
                    System.err.println("=======================================================");
                    continue;
                } else {
                    //найдений - оновити дату народження
                    //Комірка 4 - Дата рождения
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                    java.util.Date d = row.getCell(3).getDateCellValue();
                    LocalDate localDate = LocalDate.ofInstant(d.toInstant(), ZoneId.systemDefault());
                    prepInDB.setDr(localDate);
                    prepodServiceImpl.updatePrepod(prepInDB);
                    prepInDB = prepodServiceImpl.getPrepodByExapmleWithDr(prepInDB);
                    //вивести інформацію
                    System.out.println("=======================================================");
                    System.out.println("FULL INFO: ");
                    System.out.println(prepInDB.getFam() + " " + prepInDB.getImya() + " " + prepInDB.getOtch()
                            + " " + prepInDB.getDr().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    //Комірка 17 - Наказ про работу
                    cellText = row.getCell(16).getStringCellValue();
                    System.out.println("Місце роботи - " + cellText);
                    System.out.println("=======================================================");
                }
                //System.out.println(prepInDB);
                //if was found get values other cells

                //Військова інформація
                //Читання і додавання (або оновлення)

//=======================================================
//=======================================================
//11=====================================================
                // Читання
                MilitaryPerson militaryPerson = new MilitaryPerson();
                try {
                    militaryPerson.setPrepod(prepInDB);
                    //Комірка 2 військове звання - приводим ко всем маленьким
                    String vzvanieName = row.getCell(1).getStringCellValue().toLowerCase();
//                System.out.println(vzvanieName);
                    VZvanie vzvanie = vzvanieServiceImpl.getVzvanieByName(vzvanieName);
                    if (vzvanie == null) {
                        System.err.println("Військове звання " + vzvanieName + " не знайдене");
                        throw new RuntimeException("Військове звання " + vzvanieName + " не знайдене");
                    }
                    militaryPerson.setVZvanie(vzvanie);
                    //Комірка 6 ВОС
                    String vos = row.getCell(5).getStringCellValue();
//                System.out.println(vos);
                    militaryPerson.setVos(vos);
                    //Комірка 7 Состав
                    String skladStr = row.getCell(6).getStringCellValue();
//                System.out.println(sklad);
                    VSklad vsklad = vskladServiceImpl.getVSkladByName(skladStr);
                    if (vsklad == null) {
                        System.err.println("Категорія обліку  " + skladStr + " не знайдена");
                        throw new RuntimeException("Категорія обліку " + skladStr + " не знайдена");
                    }
                    militaryPerson.setVSklad(vsklad);
                    // Група обліку "військовозобов\'язаний", "призовник" - альтернатива
                    militaryPerson.setVGrupa("військовозобов\'язаний");
                    //Комірка 8 Категорія обліку (2 , sometime 1)
                    String category = String.valueOf((int) row.getCell(7).getNumericCellValue());
//                System.out.println(category);
                    militaryPerson.setVCategory(Integer.valueOf(category));
                    //Комірка 13 Военкомат
                    String voenkomatName = row.getCell(12).getStringCellValue();
//                System.out.println(voenkomatName);
                    Voenkomat voenkomat = voenkomatServiceImpl.getVoenkomatByName(voenkomatName);
                    if (voenkomat == null) {
                        System.err.println("Военкомат  " + skladStr + " не знайдений");
                        throw new RuntimeException("Военкомат " + skladStr + " не знайдений");
                    }
                    militaryPerson.setVoenkomat(voenkomat);
                    //Комірка 14 Спец облік -  не обробляємо
                    String specOblik = row.getCell(13).getStringCellValue();
//                System.out.println(specOblik);
                    militaryPerson.setReserv("немає");
                    //Комірка 15 Придатність
                    String goden = row.getCell(14).getStringCellValue();
//                System.out.println(goden);
                    militaryPerson.setVPrydatnist(goden);
                    //Комірка 9 - Освіта
                    cellText = row.getCell(8).getStringCellValue();
//                System.out.println("Освіта - " + cellText);
                    String[] osvitaBloks = cellText.split(";");
                    //контролировать в ячейках наявність тексту типу "вища", "повна вища", "вища спеціальна" і ";"
                    //Відомості про закінчення внз вказувати, додаючи ";"
                    String osvitaLevel = osvitaBloks[0];
                    militaryPerson.setEducationLevel(osvitaLevel);

                    //Комірка 16 - Состав семьи
                    cellText = row.getCell(15).getStringCellValue();
//                System.out.println("Склад родини - " + cellText);
                    String[] familyMembersStr = cellText.split(";");
                    //контролировать в ячейках наявність тексту типу "одружений", "неодружений", "розлучений" і ";"
                    //Відомості про членів родини вказувати, додаючи ";"
                    String familyState = familyMembersStr[0];
                    militaryPerson.setFamilyState(familyState);
//                    System.out.println(militaryPerson);
                    //Всі дані зібрані
//                    MilitaryPerson militaryPersonInDB = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepInDB);
//                    if (militaryPersonInDB == null) {
////                створити новий та зберігти
//                        militaryPerson = militaryPersonServiceImpl.
//                                saveMilitaryInfo(prepInDB, voenkomatName, vzvanieName, skladStr,
//                                        vos, Integer.parseInt(category), "військовозобов\'язаний",
//                                        goden, "немає", osvitaLevel, familyState);
//                    } else {
//                        //оновити
//                        militaryPersonServiceImpl.updateMilitaryPerson(militaryPersonInDB.getId(), militaryPerson);
//                    }
                } catch (RuntimeException ex) {
                    System.err.println("Дані військового обліку для " + prepInDB.getFam() + " містять помилку ");
                }
//=======================================================
//=======================================================
//22=====================================================
                //Работа
                //Комірка 17 - Наказ про работу
                cellText = row.getCell(16).getStringCellValue();
//                System.out.println("Місце роботи - " + cellText);
//                CurrentDoljnostInfo work = parseNakaz(prepInDB, cellText);
//                if (work != null) {
//                    // System.out.println(work);
//                    CurrentDoljnostInfo workInDB = currentDoljnostInfoServiceImpl.getCurrentDoljnostInfoByPrepodId(prepInDB.getId());
//                    if (workInDB == null) {
//                        // ще немає даних про работу
//                        currentDoljnostInfoServiceImpl.createCurrentDoljnostInfo(work);
//                        prepInDB.setPosadaNakazy(work);
//                        prepodServiceImpl.updatePrepod(prepInDB);
//                    } else {
//                        currentDoljnostInfoServiceImpl.updateCurrentDoljnostInfo(workInDB.getId(), work);
//                    }
//                } else {
//                    System.err.println("Дані про наказ відсутні");
//                    if (prepInDB.getPosadaNakazy() != null) {
//                        currentDoljnostInfoServiceImpl.deleteCurrentDoljnostInfo(prepInDB.getPosadaNakazy().getId());
//                        prepInDB.setPosadaNakazy(null);
//                        prepodServiceImpl.updatePrepod(prepInDB);
//                    }
//                }
//=======================================================
//=======================================================
//33=====================================================
                //Адреси - регістрація та фактичне проживання
                //Комірка 11 - адреса регістрації
                //Комірка 12 - фактичне проживання
                cellText = row.getCell(10).getStringCellValue();
//                System.out.println("Адреса прописки - " + cellText);
                String cellText2 = row.getCell(11).getStringCellValue();
//                if (cellText.equals(cellText2)) {
//                   System.out.println("Адреса фактичне проживання співпадає із пропискою!");
//                } else {
//                    System.out.println("Адреса фактичне проживання - " + cellText2);
//                }
//                PersonalData contacts = parsePersonalDate(prepInDB, cellText, cellText2);
//                if (contacts != null) {
//                    // System.out.println(work);
//                    PersonalData contactsInDB = personalDataServiceImpl.getPersonalDataByPrepodId(prepInDB.getId());
//                    if (contactsInDB == null) {
//                        // ще немає даних про работу
//                        personalDataServiceImpl.createPersonalData(contacts);
//                        prepInDB.setContacts(contacts);
//                        prepodServiceImpl.updatePrepod(prepInDB);
//                    } else {
//                        contacts.setId(contactsInDB.getId());
//                        personalDataServiceImpl.updatePersonalData(contacts);
//                    }
//                } else {
//                    System.err.println("Контактні дані представлені із помилками");
//                    if (prepInDB.getContacts() != null) {
//                        personalDataServiceImpl.deletePersonalData(prepInDB.getContacts().getId());
//                        prepInDB.setContacts(null);
//                        prepodServiceImpl.updatePrepod(prepInDB);
//                    }
//                }
//=======================================================
//=======================================================
//44=====================================================
                //Документи
                //Комірка 10 - Паспорт
                //Зчитується з передбачення, що у таблиці або ид паспорт, або звичайний паперовий
                //Надалі може бути ще паперовий, але ИД та Паперовий одночасно через інтерфейс не вводиться
                //Це створюється для початкового заповнення БД, тому і реалізовано на мінімалках -
                //Якщо новий док є = все вилучається, новий додається
                //інакше - все на місці
                cellText = row.getCell(9).getStringCellValue();
//                Document doc = parseDok(prepInDB, cellText);
//                //System.out.println(doc);
//                if (doc != null) {
//                    prepInDB.delDocuments();
//                    documentServiceImpl.deleteDocumentsByPrepod(prepInDB);
//                    documentServiceImpl.createDocument(doc);
//                    Document docInDB = documentServiceImpl.getDocumentByExample(doc);
//                    prepInDB.addDocument(docInDB);
//                } else System.err.println("Дані паспорту відсутні або некоректні");
//=======================================================
//=======================================================
//55=====================================================
                //Склад родини
                //Комірка 16 - Состав семьи
                cellText = row.getCell(15).getStringCellValue();
//                System.out.println("Склад родини - " + cellText);
                String[] familyMembersStr = null;
                familyMembersStr = cellText.split(";");
                //контролировать в ячейках наявність тексту типу "одружений", "неодружений", "розлучений" і ";"
                //Відомості про членів родини вказувати, додаючи ";"
//                String familyStat = familyMembersStr[0];
//                militaryPerson.setFamilyState(familyStat);
                //якщо нічого більше немає, то все
//                Set<FamilyMember> relatives = new LinkedHashSet<>();
//                if (familyMembersStr.length > 1) {
//                    for (int i = 1; i < familyMembersStr.length; i++) {
//                        FamilyMember familyMember = parseFamilyMember(prepInDB, familyMembersStr[i]);
//                        if (familyMember!=null) {
//                            relatives.add(familyMember);
//                        }
//                    }
//                }
//                if (!relatives.isEmpty()) {
//                    prepInDB.delFamily();
//                    familyMemberServiceImpl.deleteAllByPrepod(prepInDB);
//                    for (FamilyMember fm: relatives) {
//                        familyMemberServiceImpl.createFamilyMember(fm);
//                        FamilyMember fmInDB = familyMemberServiceImpl.getFamilyMemberByExample(fm);
//                        prepInDB.addMember(fmInDB);
//                    }
//                } else {
//                    System.err.println("Відомості про членів родини відсутні або некоректні");
//                }
//=======================================================
//=======================================================
//77=====================================================
                //Oсвіта
                //Комірка 9 - Освіта
                cellText = row.getCell(8).getStringCellValue();
//                System.out.println("Освіта - " + cellText);
                String[] osvitaBloks = cellText.split(";");
//                osvitaBloks = cellText.split(";");
//                //контролировать в ячейках наявність тексту типу "вища", "повна вища", "вища спеціальна" і ";"
//                //Відомості про закінчення внз вказувати, додаючи ";"
////                String osvitaLevel = osvitaBloks[0];
////                System.out.println(osvitaLevel);

                Set<Education> eduList = new LinkedHashSet<>();
                //якщо нічого більше немає, то все
                if (osvitaBloks.length > 1) {
                    for (int i = 1; i < osvitaBloks.length; i++) {
                        Education eduItem = parseEducation(prepInDB, osvitaBloks[i]);
                        if (eduItem != null) {
                            //Треба витягувати дані по внз і додавати, якщо не в базі
                            //Витягуемо внз, що распарсений у БД
                            VNZaklad vznEduItem = eduItem.getVnz();
                            //Чи є внз із такою скороченою назвою у БД
                            Long idVNZ1 = null;
                            Long idVNZ2 = null;
                            VNZaklad vznEduItemInDB = null;
                            idVNZ1 = vnzServiceImpl.findVNZakladByVnzShortName(vznEduItem.getVnzShortName());
                            if (idVNZ1==null) {
                                //Нема. Перевіряємо повну. Якщо раніше була із помилками - це не до нас
                                //А ще вона може бути пустою - тоді не робимо нічого
                                if (vznEduItem.getVnzName()!=null && vznEduItem.getVnzName().trim().length()>0) {
                                    idVNZ2= vnzServiceImpl.findVNZakladByVnzName(vznEduItem.getVnzName());
                                    if (idVNZ2==null) {
                                        //Новий ВНЗ із скороченою та повною назвою
                                        vnzServiceImpl.createVNZaklad(vznEduItem);
                                        //Сохранили и нашли снова - уже нулевой не будет
                                        idVNZ2= vnzServiceImpl.findVNZakladByVnzName(vznEduItem.getVnzName());
                                        vznEduItemInDB = vnzServiceImpl.getVNZakladById(idVNZ2);
                                    } else {
                                        //Есть с такою повною назвою - берется, а скорочена ігнорується
                                        vznEduItemInDB = vnzServiceImpl.getVNZakladById(idVNZ2);
                                    }
                                } else {
                                    //Тобто нове скорочене імя і нема повного, то також створюємо новий
                                    vnzServiceImpl.createVNZaklad(vznEduItem);
                                    //Сохранили и нашли снова - уже нулевой не будет. Но ищем по сокращенному имени
                                    idVNZ1= vnzServiceImpl.findVNZakladByVnzShortName(vznEduItem.getVnzShortName());
                                    vznEduItemInDB = vnzServiceImpl.getVNZakladById(idVNZ1);
                                }
                            } else {
                                //Есть с таким коротким именем. Предполагаю, что и длинное совпадает без проверки
                                //Если єто не так - ну, значит, не так и исправиться потом ручками
                                vznEduItemInDB = vnzServiceImpl.getVNZakladById(idVNZ1);
                            }
                            //Після наступної команди у освіти буде внз, що є у БД
                            eduItem.setVnz(vznEduItemInDB);
                            eduItem.setFormTraining("Денна");
                            eduList.add(eduItem);
                        }
                    }
                }
                if (!eduList.isEmpty()) {
                    prepInDB.delEducationList();
                    educationServiceImpl.deleteAllByPrepod(prepInDB);
                    for (Education eduItem: eduList) {
                        educationServiceImpl.createEducation(eduItem);
                        Education eduItemInDB = educationServiceImpl.
                                getEducationByKey(prepInDB,eduItem.getVnz(),eduItem.getYearVypusk());
                        prepInDB.addEducation(eduItemInDB);
                    }
                } else {
                    System.err.println("Відомості про освіту відсутні або некоректні");
                }

                System.out.println(militaryPerson);
                System.out.println("====================== end prepod " + prepod.getFam() + " ==========================");

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
            String kodOsvitaLevel = eduString.substring(0, pozDviKrapky);
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

    public String levelOsvita(String levelKod) {
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

    public Document parseDok(Prepod prep, String cellValue) {
        Document pDok = null;

        String cellVal = cellValue.trim();
//        char[] stringAsChar = cellVal.toCharArray();
//        System.out.println(Arrays.toString(stringAsChar));
        Pattern pattern = Pattern.compile(regex_pasport);
        Matcher matcher = pattern.matcher(cellVal);

        if (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
            pDok = new Document();
            pDok.setPrepod(prep);
            pDok.setDocType("Паперовий паспорт");
            pDok.setDocNumber(matcher.group(1) + matcher.group(2));
            pDok.setKtoVyd(matcher.group(3));
            pDok.setDataVyd(LocalDate.parse(matcher.group(4), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            pattern = Pattern.compile(regex_idcard);
            matcher = pattern.matcher(cellVal);
            if (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
                pDok = new Document();
                pDok.setPrepod(prep);
                pDok.setDocType("ID CARD");
                pDok.setDocNumber(matcher.group(1));
                pDok.setKtoVyd(matcher.group(2));
                pDok.setDataVyd(LocalDate.parse(matcher.group(3), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
        }
        return pDok;

    }

    private static final String regex_work = "наказ\\s*№(\\d+)\\s*від\\s*(\\d{2}\\.\\d{2}\\.\\d{4})";

    private CurrentDoljnostInfo parseNakaz(Prepod prep, String cellValue) {
        CurrentDoljnostInfo work = null;

        String cellVal = cellValue.trim();

        Pattern pattern = Pattern.compile(regex_work);
        Matcher matcher = pattern.matcher(cellVal);

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
            work.setPrepod(prep);
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
                        if (nameParts.length == 1) {
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
                            if (nameParts.length == 1) {
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

    private static final String regex_sity_address = "((^м\\.?|м\\.?|смт) ?[А-Яа-яїіє ]+),([0-9А-Яа-яЇїІіЄєҐґ \\.\\,\\/-]+)";


    public PersonalData parsePersonalDate(Prepod prep, String address1, String address2) {
        PersonalData pd = null;
        Country country = countryServiceImpl.getCountryByName("Україна");
        RegionUkraine regUA1 = null;
        int comaPos1 = -1;
        String addr1 = "";
        String obl1 = "";
        String city1 = "";
        if (address1.contains("обл.")) {
            comaPos1 = address1.indexOf(",");
            obl1 = address1.substring(0, comaPos1);
            regUA1 = regionUkraineServiceImpl.getRegionUkraineByName(obl1);
        }
        addr1 = address1.substring(comaPos1 + 1);

        Pattern pattern = Pattern.compile(regex_sity_address);
        Matcher matcher = pattern.matcher(addr1);

        if (matcher.find()) {

            city1 = matcher.group(1).trim();
            addr1 = matcher.group(3).trim();
//            System.out.println("Address1:");
//            System.out.println("region - " + obl1);
//            System.out.println("city - " + city1);
//            System.out.println("addr - " + addr1);
        }
        if (!address1.equals(address2)) {
            RegionUkraine regUA2 = null;
            int comaPos2 = -1;
            String addr2 = "";
            String obl2 = "";
            String city2 = "";
            if (address2.contains("обл.")) {
                comaPos2 = address1.indexOf(",");
                obl2 = address2.substring(0, comaPos2);
                regUA2 = regionUkraineServiceImpl.getRegionUkraineByName(obl2);
            }
            addr2 = address2.substring(comaPos2 + 1);

            pattern = Pattern.compile(regex_sity_address);
            matcher = pattern.matcher(addr2);

            if (matcher.find()) {
                city2 = matcher.group(1).trim();
                addr2 = matcher.group(3).trim();
//                System.out.println("Address2:");
//                System.out.println("region - " + obl2);
//                System.out.println("city - " + city2);
//                System.out.println("addr - " + addr2);
                if (obl2 != null) {
                    pd = new PersonalData(prep, country, regUA1, city1, addr1, country, regUA2, city2, addr2);
                } else {
                    pd = new PersonalData(prep, country, regUA1, city1, addr1, null, null, city2, addr2);
                }
            }
        } else {
            //присвоить однакові адреси 1 та 2
            pd = new PersonalData(prep, country, regUA1, city1, addr1, country, regUA1, city1, addr1);
        }
//        System.out.println(pd);
        return pd;
    }

}
