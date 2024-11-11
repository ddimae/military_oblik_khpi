package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.CathedraAdapter;
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
    public void addCathedra(CathedraAdapter adapter) {

        Kafedra kafedra = new Kafedra();

        kafedra.setKname(adapter.getFullName());
        kafedra.setKabr(adapter.getAbbr());
        kafedra.setOid(adapter.getCode());
        kafedra.setFakultet(fakultetService.findFakultetByFname(adapter.getInstitute()));
        kafedraService.createKafedra(kafedra);

    }
}
