package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.javafxutils.Popup;
import ntukhpi.semit.militaryoblik.service.*;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MilitaryRegistrationCRUD {

    @Autowired
    MilitaryPersonService militaryPersonService;
    @Autowired
    PrepodService prepodService;
    @Autowired
    VoenkomatService voenkomatService;

    @Autowired
    VSkladService vSkladService;
    @Autowired
    VZvanieService vZvanieService;

    public void updateEducation(Long idPerson,
                                String vos, String category, String group, String vSklad, String vZvanie,
                                String prydatnist, String voenkomatName, String familyState, String educationLevel) {


        MilitaryPerson militaryPerson = militaryPersonService
                .getMilitaryPersonByPrepod(prepodService.getPrepodById(idPerson));

      //  militaryPerson.setPrepod(selectedPrepod); ВІН ВЖЕ ПРИЗНАЧЕНИЙ У militaryPerson
        militaryPerson.setVos(vos);
        militaryPerson.setVCategory(Integer.parseInt(category));
        militaryPerson.setVGrupa(group);

        militaryPerson.setVSklad(vSkladService.getVSkladByName(vSklad));
        militaryPerson.setVZvanie(vZvanieService.getVzvanieByName(vZvanie));
        militaryPerson.setVPrydatnist(prydatnist);

        Voenkomat voenkomat = voenkomatService.getVoenkomatByName(voenkomatName);
        if (voenkomat == null) {
// Якщо новий, то треба додати
            voenkomat = new Voenkomat();
            voenkomat.setVoenkomatName(voenkomatName);
            voenkomat = voenkomatService.createVoenkomat(voenkomat);
        }
        militaryPerson.setVoenkomat(voenkomat);
        militaryPerson.setFamilyState(familyState);
        militaryPerson.setEducationLevel(educationLevel);

        militaryPersonService.updateMilitaryPerson(militaryPerson.getId(), militaryPerson);
    }

}
