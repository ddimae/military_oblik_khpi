package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.EducationPostgraduateAdapter;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.service.EducationPostgraduateService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationPostgraduateCRUD {

    @Autowired
    EducationPostgraduateService educationPostgraduateService;

    @Autowired
    VNZakladServiceImpl vnZakladService;

    @Autowired
    PrepodServiceImpl prepodService;

    public void addPostgraduateEducation(Long idPerson, EducationPostgraduateAdapter adapter) {
        EducationPostgraduate newEducationPostgraduate = createNewInstance(idPerson, adapter);
        educationPostgraduateService.createEducationPostgraduate(newEducationPostgraduate);

    }

    public void updatePostgraduateEducation(Long idPersonForUpdate,Long idPostEducation, EducationPostgraduateAdapter adapter) {
        EducationPostgraduate educationPostgraduateUpdate = createNewInstance(idPersonForUpdate, adapter);
        educationPostgraduateService.updateEducationPostgraduate(idPostEducation, educationPostgraduateUpdate);
    }

    public void deletePostgraduateEducation(Long idDelEducation) {
        educationPostgraduateService.deleteEducationPostgraduate(idDelEducation);
    }


    private EducationPostgraduate createNewInstance(Long idPerson, EducationPostgraduateAdapter adapter) {

        EducationPostgraduate newEducationPostgraduate = new EducationPostgraduate();

        newEducationPostgraduate.setPrepod(prepodService.getPrepodById(idPerson));
        newEducationPostgraduate.setYearFinish(adapter.getYear());
        newEducationPostgraduate.setLevelTraining(adapter.getType());
        newEducationPostgraduate.setVnz(vnZakladService.getVNZakladById(vnZakladService.findIdVNZakladByVnzShortName(adapter.getVnz().getShortName())));
        // Зараз в БД немає номеру диплома та спеціальності
        //Но така інформація не потрібна для представлення у додатку 05, але передбачається формою П-2
//        newEducation.setDiplomaSeries(diplomaSeries);
//        newEducation.setDiplomaNumber(diplomaNumber);
//        newEducation.setDiplomaSpeciality(specialtyNumber);
//        newEducation.setDiplomaSpeciality(specialtyNazva);
        return newEducationPostgraduate;
    }
}
