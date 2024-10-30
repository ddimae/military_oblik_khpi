package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.PrepodAdapter;
import ntukhpi.semit.militaryoblik.entity.MilitaryPerson;
import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Kafedra;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MilitaryRegistrationCRUD {

    @Autowired
    MilitaryPersonService militaryPersonService;
    @Autowired
    PrepodService prepodService;

    @Autowired
    KafedraService kafedraService;
    @Autowired
    VoenkomatService voenkomatService;

    @Autowired
    VSkladService vSkladService;
    @Autowired
    VZvanieService vZvanieService;

    public MilitaryPerson updateMilitaryPerson(Long idPerson,
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

        return militaryPersonService.updateMilitaryPerson(militaryPerson.getId(), militaryPerson);
    }

    public MilitaryPerson createBaseMilitaryPerson(Prepod newPerson) {
        if (newPerson == null) return null;
        MilitaryPerson newMilitaryPerson = new MilitaryPerson();

        newMilitaryPerson.setPrepod(newPerson);
        newMilitaryPerson.setVos("000000");
        newMilitaryPerson.setVCategory(2);
        newMilitaryPerson.setVGrupa("військовозобов'язаний");

        // Для базового обліку створюється записаний склад "Не визначений" із id=0l, якщо він не був доданий раніше
        VSklad vSklad = vSkladService.getVSkladByName("Не визначений");
        if (vSklad==null) {
            vSklad = new VSklad();
            vSklad.setSkladName("Не визначений");
            vSklad.setNumOrderShow(Integer.valueOf(0));
            vSklad.setId(0L);
            vSklad = vSkladService.createVSklad(vSklad);
        }
        newMilitaryPerson.setVSklad(vSklad);

        // Для базового обліку створюється запис про військове звання "Не визначене" із id=0l, якщо віна не була додана раніше
        VZvanie vZvanie = vZvanieService.getVzvanieByName("Не визначенe");
        if (vZvanie==null) {
            vZvanie = new VZvanie();
            vZvanie.setZvanieName("Не визначенe");
            vZvanie.setKodSkladu(1);
            vZvanie.setNumOrderShow(Integer.valueOf(0));
            vZvanie.setId(0L);
            vZvanie = vZvanieService.createVZvanie(vZvanie);
        }
        newMilitaryPerson.setVZvanie(vZvanie);

        newMilitaryPerson.setVPrydatnist("Не вказана");

        // Для базового обліку створюється военкомат "Не визначений" із id=0l, якщо він не був доданий раніше
        Voenkomat voenkomat = voenkomatService.getVoenkomatByName("Не визначений");
        if (voenkomat==null) {
            voenkomat = new Voenkomat();
            voenkomat.setVoenkomatName("Не визначений");
            voenkomat.setId(0L);
            voenkomat = voenkomatService.createVoenkomat(voenkomat);
        }
        newMilitaryPerson.setVoenkomat(voenkomat);
        newMilitaryPerson.setReserv("немає");
        newMilitaryPerson.setFamilyState("Не вказаний");
        newMilitaryPerson.setEducationLevel("Не вказаний");

        return militaryPersonService.createMilitaryPerson(newMilitaryPerson);
    }

    public MilitaryPerson getMilitaryPersonByPrepod(Prepod prepod) {
        return militaryPersonService.getMilitaryPersonByPrepod(prepod);
    }

    public void deleteMilitaryPerson(Long idMP) {
        militaryPersonService.deleteMilitaryPerson(idMP);
    }

    public void deleteMilitaryPersonByPrepod(Prepod prepod) {
        militaryPersonService.deleteMilitaryPerson(militaryPersonService.getMilitaryPersonByPrepod(prepod).getId());
    }


    public MilitaryPerson getMilitaryPersonByPrepod(PrepodAdapter prepodAdapter) {
        Kafedra kafEmpl = kafedraService.getKafedraByName(prepodAdapter.getCathedra());
        Prepod prepod = kafEmpl==null? null : prepodService.getEmployeeByFullKeySet(prepodAdapter.getSurname(),
                prepodAdapter.getName(),prepodAdapter.getMidname(),prepodAdapter.getCathedra());
        return getMilitaryPersonByPrepod(prepod);
    }
}
