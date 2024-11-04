package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.Document;
import ntukhpi.semit.militaryoblik.entity.FamilyMember;
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

    public void addFamilyMember(Long idPerson, String vidRidstva, String surname,
                                String name, String patronimic, String year) {
        FamilyMember newFamilyMember = createNewInstance(idPerson, vidRidstva,
                                                  surname, name, patronimic,year);
        FamilyMember newFamilyMemberInDB = familyMemberService.getFamilyMemberByExample(newFamilyMember);
        if (newFamilyMemberInDB == null) {
            familyMemberService.createFamilyMember(newFamilyMember);
        } else {
            throw new RuntimeException(MSG_DOUBLE_MEMBER);
        }
    }

    public void updateFamilyMember(Long idUpdateFamilyMember, Long idPersonForUpdate, String vidRidstva, String surname,
                               String name, String patronimic, String year) {
        FamilyMember familyMemberUpdate = createNewInstance(idPersonForUpdate, vidRidstva,
                surname, name, patronimic,year);
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


    private FamilyMember createNewInstance(Long idPerson, String vidRidstva, String surname,
                                       String name, String patronimic, String year) {

        FamilyMember newFamilyMember = new FamilyMember();

        newFamilyMember.setPrepod(prepodService.getPrepodById(idPerson));
        newFamilyMember.setVidRidstva(vidRidstva);
        newFamilyMember.setMemFam(surname);
        newFamilyMember.setMemImya(name);
        newFamilyMember.setMemOtch(patronimic);
        newFamilyMember.setRikNarodz(year);
        return newFamilyMember;
    }
}
