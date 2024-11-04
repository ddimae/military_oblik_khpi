package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.service.VNZakladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VNZCRUD {
    @Autowired
    VNZakladService vnZakladService;

//    @Autowired
//    private final VNZakladService vnZakladService;
//    public VNZCRUD(VNZakladService vnZakladService) {
//        this.vnZakladService = vnZakladService;
//    }

//    //DDE - Ось так чомусь не працює, видає NullPointerException
//    А чому - не розумію. Поки думка - має бути файнал?!
//    @Autowired
//    private VNZakladService vnZakladService;

    public void addVNZ(String name, String abbreviation) {
        VNZaklad newVNZ = new VNZaklad();
        newVNZ.setVnzName(name);
        newVNZ.setVnzShortName(abbreviation);
        vnZakladService.createVNZaklad(newVNZ);
    }
}
