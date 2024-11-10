package ntukhpi.semit.militaryoblik.service.entitycrud;

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

    public void addPostgraduateEducation(Long idPerson, String year, String type, String vnzShortName) {
        EducationPostgraduate newEducationPostgraduate = createNewInstance(idPerson, year, type, vnzShortName);
        educationPostgraduateService.createEducationPostgraduate(newEducationPostgraduate);

    }

    public void updatePostgraduateEducation(Long idPersonForUpdate, String year, String type, String vnzShortName) {
        EducationPostgraduate educationPostgraduateUpdate = createNewInstance(idPersonForUpdate, year, type, vnzShortName);
        educationPostgraduateService.updateEducationPostgraduate(idPersonForUpdate, educationPostgraduateUpdate);
    }

    public void deletePostgraduateEducation(Long idDelEducation) {
        educationPostgraduateService.deleteEducationPostgraduate(idDelEducation);
    }


    private EducationPostgraduate createNewInstance(Long idPerson, String year, String type, String vnzShortName
//         , String diplomaSeries, String diplomaNumber, String specialtyNumber, String specialtyNazva
    ) {

        EducationPostgraduate newEducationPostgraduate = new EducationPostgraduate();

        newEducationPostgraduate.setPrepod(prepodService.getPrepodById(idPerson));
        newEducationPostgraduate.setYearFinish(year);
        newEducationPostgraduate.setLevelTraining(type);
        newEducationPostgraduate.setVnz(vnZakladService.getVNZakladById(vnZakladService.findIdVNZakladByVnzShortName(vnzShortName)));
        // Зараз в БД немає номеру диплома та спеціальності
        //Но така інформація не потрібна для представлення у додатку 05, але передбачається формою П-2
//        newEducation.setDiplomaSeries(diplomaSeries);
//        newEducation.setDiplomaNumber(diplomaNumber);
//        newEducation.setDiplomaSpeciality(specialtyNumber);
//        newEducation.setDiplomaSpeciality(specialtyNazva);
        return newEducationPostgraduate;
    }
}
