package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.FamilyAdapter;
import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.DocumentService;
import ntukhpi.semit.militaryoblik.service.FamilyMemberService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FamilyMemberCRUD {

    private final String MSG_DOUBLE_MEMBER = "У базі даних є відомості про такого члена родини!";

    @Autowired
    PrepodServiceImpl prepodService;

    @Autowired
    FamilyMemberService familyMemberService;

    public void addFamilyMember(Long idPerson, FamilyAdapter adapter) {
        FamilyMember newFamilyMember = createNewInstance(idPerson, adapter);
        FamilyMember newFamilyMemberInDB = familyMemberService.getFamilyMemberByExample(newFamilyMember);
        if (newFamilyMemberInDB == null) {
            familyMemberService.createFamilyMember(newFamilyMember);
        } else {
            throw new RuntimeException(MSG_DOUBLE_MEMBER);
        }
    }

    public void updateFamilyMember(Long idUpdateFamilyMember, Long idPersonForUpdate, FamilyAdapter adapter) {
        FamilyMember familyMemberUpdate = createNewInstance(idPersonForUpdate, adapter);
        FamilyMember newFamilyMemberInDB = familyMemberService.getFamilyMemberByExample(familyMemberUpdate);
        if (newFamilyMemberInDB == null || (newFamilyMemberInDB != null && newFamilyMemberInDB.getId()==idUpdateFamilyMember)) {
            familyMemberService.updateFamilyMember(idUpdateFamilyMember, familyMemberUpdate);
        } else {
            throw new RuntimeException(MSG_DOUBLE_MEMBER);
        }
    }

    public void deleteFamilyMember(Long idDelFamilyMember) {
        familyMemberService.deleteFamilyMember(idDelFamilyMember);
    }

    public void deleteAllFamilyByPrepodId(Long prepodId) {
        Prepod prepod = new Prepod();

        prepod.setId(prepodId);
        familyMemberService.deleteAllByPrepod(prepod);
    }

    private FamilyMember createNewInstance(Long idPerson, FamilyAdapter adapter) {

        FamilyMember newFamilyMember = new FamilyMember();

        newFamilyMember.setPrepod(prepodService.getPrepodById(idPerson));
        newFamilyMember.setVidRidstva(adapter.getVidRidstva());
        newFamilyMember.setMemFam(adapter.getMemFam());
        newFamilyMember.setMemImya(adapter.getMemName());
        newFamilyMember.setMemOtch(adapter.getMemOtch());
        newFamilyMember.setRikNarodz(adapter.getRikNarodz());
        return newFamilyMember;
    }
}
