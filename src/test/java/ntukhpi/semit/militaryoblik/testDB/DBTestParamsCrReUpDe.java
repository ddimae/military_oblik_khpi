package ntukhpi.semit.militaryoblik.testDB;

import ntukhpi.semit.militaryoblik.service.entitycrud.VNZCRUD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBTestParamsCrReUpDe {
//    @Autowired
//    VNZakladService vnZakladService;
    @Autowired
    VNZCRUD vnzCRUD;

    @Test
    void insertVNZ() {
        try {
            vnzCRUD.addVNZ("Полтавський інститут зв'язку", "ПІЗ");
        } catch (Exception e) {
            System.err.println("May be dublicate?!");
        }
    }
}
