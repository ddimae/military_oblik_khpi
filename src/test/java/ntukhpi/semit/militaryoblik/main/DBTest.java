package ntukhpi.semit.militaryoblik.main;

import ntukhpi.semit.militaryoblik.adapters.ReservistAdapter;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Dolghnost;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.*;
import ntukhpi.semit.militaryoblik.service.DolghnostServiceImpl;
import ntukhpi.semit.militaryoblik.service.KafedraService;
import ntukhpi.semit.militaryoblik.service.KafedraServiceImpl;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    PrepodRepository prepodRepository;

    @Test
    void showPrepod() {

        System.out.println("\nPrepod list in SQLite:");
        List<Prepod> prepodList = prepodRepository.findAll();
        for (Prepod prep : prepodList) {
            System.out.println(prep);
        }

    }

    @Autowired
    DolghnostRepository dolghnostRepository;


    @Test
    void showDolghnost() {

        System.out.println("\nDolghnost list in SQLite:");
        List<Dolghnost> posadaList = dolghnostRepository.findAll();
        for (Dolghnost d : posadaList) {
            System.out.println(d.getDolghnName()+" "+d.getId());
        }

    }

    @Autowired
    KafedraServiceImpl kafedraServiceImpl;
    @Autowired
    DolghnostServiceImpl dolghnostServiceImpl;
    @Autowired
    PrepodServiceImpl prepodServiceImpl;
    @Test
    void findPrepod() {

        Long kid = 275L;
        Kafedra kafedra = kafedraServiceImpl.getKafedraById(kid);
        Long posadaId = 3L;
        Dolghnost posada = dolghnostServiceImpl.getDolghnostById(posadaId);
        Prepod prepod = new Prepod("Носик","Андрій", "Михайлович",kafedra,posada);
        Prepod prepodInDB = prepodServiceImpl.getPrepodByExapmle(prepod);
        if (prepodInDB!=null) {
            System.out.println(prepod.getFam()+" --> "+prepodInDB.getId());
            ReservistAdapter reserv = new ReservistAdapter(prepodInDB);
            System.out.println(reserv);
        } else {
            System.out.println(prepod.getFam()+" --> absent");
        }
        prepod = new Prepod("Носик","Андрій", "Михайловична",kafedra,posada);
        prepodInDB = prepodServiceImpl.getPrepodByExapmle(prepod);
        if (prepodInDB!=null) {
            System.out.println(prepod.getFam()+" --> "+prepodInDB.getId());
        } else {
            System.out.println(prepod.getFam()+" --> absent");
        }

    }

//    @Test
//    void createReservistAdapterList() {
//        List<ReservistAdapter> reservistsList = new ArrayList<>();
//        List<Prepod> prepodList = prepodServiceImpl.getAllPrepod();
//        System.out.println(prepodList);
//        prepodList.stream().forEach(
//                prep -> reservistsList.add(new ReservistAdapter(prep))
//        );
//        System.out.println(reservistsList);
//
//    }



}
