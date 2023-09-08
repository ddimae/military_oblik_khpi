package ntukhpi.semit.militaryoblik.main;

import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.repository.*;
import ntukhpi.semit.militaryoblik.service.*;
import ntukhpi.semit.militaryoblik.utils.Dodatok5Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ExcelReadTest {
    @Autowired
    FakultetRepository fakultetRepository;
    @Autowired
    KafedraRepository kafedraRepository;
    @Autowired
    VoenkomatRepository voenkomatRepository;
    @Autowired
    PrepodRepository prepodRepository;
    @Autowired
    DolghnostRepository dolghnostRepository;
    @Autowired
    KafedraServiceImpl kafedraServiceImpl;
    @Autowired
    DolghnostServiceImpl dolghnostServiceImpl;
    @Autowired
    PrepodServiceImpl prepodServiceImpl;
    @Autowired
    MilitaryPersonServiceImpl militaryPersonServiceImpl;
    @Autowired
    VZvanieServiceImpl vZvanieServiceImpl;
    @Test
    void testReadDodatok5() {
        System.out.println("\nRead from file");
        (new Dodatok5Reader(vZvanieServiceImpl,prepodServiceImpl,militaryPersonServiceImpl)).readExcelFileWithDodatok5();

    }


}
