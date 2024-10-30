package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.service.FakultetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstituteCRUD {

    @Autowired
    FakultetService fakultetService;
    public void addInstitute(String fullName, String abbr, String code) {

        Fakultet fakultet = new Fakultet();

        fakultet.setFname(fullName);
        fakultet.setAbr(abbr);
        fakultet.setOid(code);

        fakultetService.createFakultet(fakultet);
    }
}
