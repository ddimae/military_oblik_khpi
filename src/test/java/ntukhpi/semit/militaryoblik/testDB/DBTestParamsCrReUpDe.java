package ntukhpi.semit.militaryoblik.testDB;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.entitycrud.EmployeeCRUD;
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

    @Autowired
    EmployeeCRUD employeeCRUD;

    @Test
    void insertVNZ() {
        try {
            vnzCRUD.addVNZ("Полтавський інститут зв'язку", "ПІЗ");
        } catch (Exception e) {
            System.err.println("May be dublicate?!");
        }
    }

    @Test
    void insertEmployee() {
        employeeCRUD.addEmployee("Ара","Ака","Іванович",
                "Інформаційні системи та технології","",
                "доцент", "Не має", "Не визначено");
    }

}
