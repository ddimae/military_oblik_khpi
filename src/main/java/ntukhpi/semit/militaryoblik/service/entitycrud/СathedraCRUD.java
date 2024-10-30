package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.service.FakultetService;
import ntukhpi.semit.militaryoblik.service.KafedraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class СathedraCRUD {
    @Autowired
    KafedraService kafedraService;

    @Autowired
    FakultetService fakultetService;
    public void addCathedra(String fullName, String abbr, String code, String instituteName) {

        Kafedra kafedra = new Kafedra();

        kafedra.setKname(fullName);
        kafedra.setKabr(abbr);
        kafedra.setOid(code);
        kafedra.setFakultet(fakultetService.findFakultetByFname(instituteName));
        kafedraService.createKafedra(kafedra);

    }
}
