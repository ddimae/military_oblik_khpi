package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.InstituteAdapter;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Fakultet;
import ntukhpi.semit.militaryoblik.service.FakultetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstituteCRUD {

    @Autowired
    FakultetService fakultetService;
    public void addInstitute(InstituteAdapter adapter) {

        Fakultet fakultet = new Fakultet();

        fakultet.setFname(adapter.getName());
        fakultet.setAbr(adapter.getAbbr());
        fakultet.setOid(adapter.getCode());

        fakultetService.createFakultet(fakultet);
    }
}
