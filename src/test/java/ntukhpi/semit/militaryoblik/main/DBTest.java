package ntukhpi.semit.militaryoblik.main;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.repository.FakultetRepository;
import ntukhpi.semit.militaryoblik.repository.KafedraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
