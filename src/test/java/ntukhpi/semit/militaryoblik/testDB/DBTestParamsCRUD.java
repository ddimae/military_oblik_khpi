package ntukhpi.semit.militaryoblik.testDB;

import ntukhpi.semit.militaryoblik.service.VNZakladService;
import ntukhpi.semit.militaryoblik.service.entitiescrud.VNZCRUD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBTestParamsCRUD {
    @Autowired
    VNZakladService vnZakladService;

    @Test
    void insertVNZ() {
        try {
            (new VNZCRUD(vnZakladService)).addVNZ("Полтавський інститут зв\'язку", "ПІЗ");
        } catch (Exception e) {
            System.err.println("May be dublicate?!");
        }
    }
}
