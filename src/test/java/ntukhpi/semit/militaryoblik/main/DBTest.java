package ntukhpi.semit.militaryoblik.main;

import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.*;
import ntukhpi.semit.militaryoblik.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DBTest {

    @Autowired
    FakultetRepository fakultetRepository;

    @Test
    void showFakultet() {

        System.out.println("\nFakultet list in SQLite:");
        List<Fakultet> fakultetList = fakultetRepository.findAll();
        for (Fakultet f : fakultetList) {
            System.out.println(f.getFname());
        }
    }

    @Autowired
    KafedraRepository kafedraRepository;

    @Test
    void showKafedra() {

        System.out.println("\nKafedra list in SQLite:");
        List<Kafedra> kafedraList = kafedraRepository.findAll();
        for (Kafedra kaf : kafedraList) {
            System.out.println(kaf);
        }
    }

    @Autowired
    VoenkomatRepository voenkomatRepository;

    @Test
    void showVoenkomat() {

        System.out.println("\nVoenkomat list in SQLite:");
        List<Voenkomat> voenkomatList = voenkomatRepository.findAll();
        for (Voenkomat voenkomat : voenkomatList) {
            System.out.println(voenkomat.getVoenkomatName());
        }
    }

    @Autowired
    KafedraServiceImpl kafedraServiceImpl;
    @Autowired
    DolghnostServiceImpl dolghnostServiceImpl;

    @Test
    void showDolghnost() {

        System.out.println("\nDolghnost list in SQLite:");
        List<Dolghnost> posadaList = dolghnostServiceImpl.getAllDolghnost();
        for (Dolghnost d : posadaList) {
            System.out.println(d.getDolghnName() + " " + d.getId());
        }

    }

    @Autowired
    PrepodServiceImpl prepodServiceImpl;

    @Test
    void insertPrepod() {
        Long kid = 333L;
        Kafedra kafedra = kafedraServiceImpl.getKafedraById(kid);
        Long posadaId = 16L;
        Dolghnost posada = dolghnostServiceImpl.getDolghnostById(posadaId);
        Prepod newPrepod = new Prepod("Zelenoje", "SuperChmo", "Al-vich",
                LocalDate.of(1979, 11, 25), kafedra, posada, null, null, null);
        Prepod newPrepodInDB = prepodServiceImpl.getPrepodByExapmle(newPrepod);

        if (newPrepodInDB == null) {
            System.out.println("Can be inserted!" + newPrepod.getFam() + " " + newPrepod.getDr().format(DateTimeFormatter.ISO_DATE));
            prepodServiceImpl.savePrepod(newPrepod);
            newPrepodInDB = prepodServiceImpl.getPrepodByExapmle(newPrepod);
            System.out.println(newPrepodInDB);
        } else {
            System.out.println("Such prepod almost present");
        }

    }

    @Test
    void savePrepod() {
        prepodServiceImpl.savePrepod(37l, "Арабаджи", "Тимур", "Дмитрович",
                3l, 2l, 25l, "Timur.Arabadzhy@khpi.edu.ua", "1981-07-02");
        prepodServiceImpl.savePrepod(275l, "Носик", "Андрій", "Михайлович",
                3l, 1l, 17l, "Andrii.Nosyk@khpi.edu.ua", "1974-03-16");
        prepodServiceImpl.savePrepod(288l, "Ольховий", "Олексій", "Михайлович",
                1l, 0l, 0l, "Aleksey.Olhovoy@khpi.edu.ua", "1975-07-15");
        prepodServiceImpl.savePrepod(30l, "Перепелиця", "Іван", "Дмитрович",
                3l, null, 17l, null, "1986-03-08");
        prepodServiceImpl.savePrepod(194l, "Пугачов", "Роман", "Володимирович",
                3l, 2l, 17l, "Roman.Puhachov@khpi.edu.ua", "1973-02-27");
        prepodServiceImpl.savePrepod(194l, "Соболь", "Максим", "Олегович",
                3l, 2l, 17l, "Maksym.Sobol@khpi.edu.ua", "1981-07-02");
        prepodServiceImpl.savePrepod(30l, "Сокол", "Володимир", "Євгенович",
                3l, 2l, 17l, "Volodymyr.Sokol@khpi.edu.ua", "1979-05-18");
        prepodServiceImpl.savePrepod(80l, "Ткачов", "Максим", "Михайлович",
                3l, 2l, 18l, "Maksym.Tkachov@khpi.edu.ua", "1985-10-28");
        prepodServiceImpl.savePrepod(51l, "Гріньов", "Денис", "Валерійович",
                3l, 1l, 17l, null, "1974-01-12");
        prepodServiceImpl.savePrepod(82l, "Дяченко", "Олександр", "Васильович",
                3l, null, 17l, "Oleksandr.Diachenko@khpi.edu.ua", "1991-12-31");
        prepodServiceImpl.savePrepod(47l, "Кашанський", "Юрій", "Володимирович",
                1l, null, 0l, null, "1995-11-23");
        prepodServiceImpl.savePrepod(30l, "Ковтун", "Владислав", "Юрійович",
                3l, null, 17l, null, "1978-08-30");
        prepodServiceImpl.savePrepod(287l, "Корольов", "Роман", "Володимирович",
                3l, 2l, 17l, "roman.korolov@khpi.edu.ua", "1974-12-03");
        prepodServiceImpl.savePrepod(31l, "Малько", "Максим", "Миколайович",
                4l, 2l, 17l, null, "1972-07-18");
        prepodServiceImpl.savePrepod(16l, "Мітцель", "Микола", "Олександрович",
                3l, 2l, 17l, "mittsel_nicholay@ukr.net", "1989-11-10");
        prepodServiceImpl.savePrepod(76l, "Бунтурі", "Юрій", "Валерійович",
                2l, null, 0l, "Bunturi.Yurii@khpi.edu.ua", "1978-08-19");
        prepodServiceImpl.savePrepod(72l, "Васильєв", "Михайло", "Ілліч",
                3l, 2l, 17l, null, "1984-07-09");
        prepodServiceImpl.savePrepod(3l, "Волков", "Олег", "Олексійович",
                3l, 2l, 17l, "volkovoleg1978@gmail.com", "1978-10-07");
        prepodServiceImpl.savePrepod(73l, "Воронкін", "Андрій", "Анатолійович",
                1l, null, 35l, null, "1993-09-22");
        prepodServiceImpl.savePrepod(47l, "Гончаров", "Євген", "Вікторович",
                3l, 2l, 17l, "Yevhen.Honcharov@khpi.edu.ua", "1981-02-23");
        prepodServiceImpl.savePrepod(11l, "Бібік", "Дмитро", "Вікторович",
                3l, 0l, 0l, null, "1978-06-08");
        prepodServiceImpl.savePrepod(333l, "Борисенко", "Олександр", "Юрійович",
                10l, null, null, null, "1983-05-30");
        prepodServiceImpl.savePrepod(194l, "Поляков", "Андрій", "Валентинович",
                10l, 1l, 17l, null, "1973-06-24");
        prepodServiceImpl.savePrepod(48l, "Пшенічников", "Дмитро", "Олексійович",
                7l, 1l, 17l, null, "1964-05-29");
        prepodServiceImpl.savePrepod(343l, "Рищенко", "Дмитро", "Олексійович",
                15l, 1l, 17l, null, "1984-04-11");
        prepodServiceImpl.savePrepod(274l, "Ромашов", "Дмитро", "Володимирович",
                8l, null, null, null, "1986-12-23");
        prepodServiceImpl.savePrepod(333l, "Зозуля", "Олександр", "Володимирович",
                14l, null, null, null, "1995-11-22");
        prepodServiceImpl.savePrepod(78l, "Татароєв", "Яків", "Володимирович",
                8l, 2l, 22l, null, "1971-02-13");
        prepodServiceImpl.savePrepod(48l, "Коліушко", "Деніс", "Георгієвич",
                7l, 1l, 17l, null, "1974-08-25");
        prepodServiceImpl.savePrepod(194l, "Куцак", "Віктор", "Анатолійович",
                13l, null, null, null, "1972-08-19");
        prepodServiceImpl.savePrepod(113l, "Нікітін", "Артем", "Олександрович",
                10l, null, null, null, "1979-12-23");
        prepodServiceImpl.savePrepod(333l, "Вайтекунас", "Ярослав", "Ігорович",
                16l, null, null, null, "1990-06-15");
    }

    @Test
    void showPrepod() {
        System.out.println("\nPrepod list in SQLite:");
        List prepodList = prepodServiceImpl.getAllPrepod();
        for (Object prep : prepodList) {
            System.out.println(prep);
        }
    }


    @Test
    void findPrepod() {

        Long kid = 37L;
        Kafedra kafedra = kafedraServiceImpl.getKafedraById(kid);
        Long posadaId = 3L;
        Dolghnost posada = dolghnostServiceImpl.getDolghnostById(posadaId);
        Prepod prepod = new Prepod("Арабаджи", "Тимур", "Дмитрович", kafedra, posada);
        Prepod prepodInDB = prepodServiceImpl.getPrepodByExapmle(prepod);
        if (prepodInDB != null) {
            System.out.println(prepod.getFam() + " --> " + prepodInDB.getId());
            MilitaryPerson mp = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepodInDB);
            if (mp!=null) {
                ReservistAdapter reserv = new ReservistAdapter(mp);
                System.out.println(reserv);
            } else {
                System.out.println("Military Data absent!");
            }
        } else {
            System.out.println(prepod.getFam() + " --> absent");
        }
        prepod = new Prepod("Носик", "Андрій", "Михайлович", kafedra, posada);
        prepodInDB = prepodServiceImpl.getPrepodByExapmle(prepod);
        if (prepodInDB != null) {
            System.out.println(prepod.getFam() + " --> " + prepodInDB.getId());
            MilitaryPerson mp = militaryPersonServiceImpl.getMilitaryPersonByPrepod(prepodInDB);
            if (mp!=null) {
                ReservistAdapter reserv = new ReservistAdapter(mp);
                System.out.println(reserv);
            } else {
                System.out.println("Military Data absent!");
            }
        } else {
            System.out.println(prepod.getFam() + " --> absent");
        }

    }

    @Test
    void saveMilitaryPerson() {
        Prepod prep = prepodServiceImpl.getPrepodById(5l);
        militaryPersonServiceImpl.saveMilitaryInfo(prep, "Шевченківський РТЦК та СП","полковник", "командний",
                "530200", 1, "військовозобов\'язаний","придатний", "немає");

    }

    @Autowired
    VZvanieServiceImpl vZvanieServiceImpl;

    @Test
    void showVZvanie() {
        System.out.println("\nVZvanie list in SQLite:");
        List<VZvanie> vZvanieList = vZvanieServiceImpl.getAllVZvanie();
        for (VZvanie v : vZvanieList) {
            System.out.println(v.getZvanieName() + " (" + v.getKodSkladu() + ")");
        }
    }

    @Test
    void defIdVZvanie() {
        System.out.println(vZvanieServiceImpl.getIdVZvanieByName("Лейтенант"));
        System.out.println(vZvanieServiceImpl.getIdVZvanieByName("Полковник"));
        System.out.println(vZvanieServiceImpl.getIdVZvanieByName("Половник"));
    }


    @Autowired
    MilitaryPersonServiceImpl militaryPersonServiceImpl;

    @Test
    void showReservist() {
        System.out.println("\nNa obliku list in SQLite:");
        List<MilitaryPerson> naOblikuList = militaryPersonServiceImpl.getAllMilitaryPerson();
        for (MilitaryPerson mp : naOblikuList) {
            System.out.println(mp.getPrepod().getFam() + " " + mp.getVos() + " " + mp.getVoenkomat().getVoenkomatName());
        }
    }

}
