package ntukhpi.semit.militaryoblik.testDB;

import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.adapters.UniversityAdapter;
import ntukhpi.semit.militaryoblik.service.entitycrud.EmployeeCRUD;
import ntukhpi.semit.militaryoblik.service.entitycrud.UniversityCRUD;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DBTestParamsCrReUpDe {
//    @Autowired
//    VNZakladService vnZakladService;
    @Autowired
UniversityCRUD vnzCRUD;

    @Autowired
    EmployeeCRUD employeeCRUD;

    @Test
    void insertVNZ() {
        try {
            vnzCRUD.addUniversity( new UniversityAdapter(null, "Полтавський інститут зв'язку", "ПІЗ"));
        } catch (Exception e) {
            System.err.println("May be dublicate?!");
        }
    }

    @Test
    void insertEmployee() {
        employeeCRUD.addEmployee(new PrepodAdapter(null, "Ара","Ака","Іванович",
                "Інформаційні системи та технології","",
                "доцент", "Не має", "Не визначено", "")); // FIXME: wrong arguments in test
    }

}
