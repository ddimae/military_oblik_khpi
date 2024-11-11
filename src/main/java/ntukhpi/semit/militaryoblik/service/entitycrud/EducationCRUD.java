package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.adapters.EducationAdapter;
import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.service.EducationService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationCRUD {

    private final String MSG_DOUBLE_EDU = "Дані про закінчення цього закладу в цьому році вже є у базі даних!";

    @Autowired
    EducationService educationService;

    @Autowired
    VNZakladServiceImpl vnZakladService;

    @Autowired
    PrepodServiceImpl prepodService;

    public void addEducation(Long idPerson, EducationAdapter adapter) {
        Education newEducation = createNewInstance(idPerson, adapter);
        Education newDocumentInDB = educationService.getEducationByKey(newEducation.getPrepod(),
                newEducation.getVnz(),newEducation.getYearVypusk());
        if (newDocumentInDB == null) {
            educationService.createEducation(newEducation);
        } else {
            throw new RuntimeException(MSG_DOUBLE_EDU);
        }


    }

    public void updateEducation(Long idEdu, Long idPersonForUpdate, EducationAdapter adapter) {
        Education educationUpdate = createNewInstance(idPersonForUpdate, adapter);
        Education newDocumentInDB = educationService.getEducationByKey(educationUpdate.getPrepod(),
                educationUpdate.getVnz(),educationUpdate.getYearVypusk());
        if (newDocumentInDB == null || (newDocumentInDB != null && newDocumentInDB.getId() == idEdu)) {
            educationService.updateEducation(idEdu, educationUpdate);
        } else {
            throw new RuntimeException(MSG_DOUBLE_EDU);
        }
    }
    public void deleteEducation(Long idDelEducation) {
        educationService.deleteEducation(idDelEducation);
    }

    private Education createNewInstance(Long idPerson, EducationAdapter adapter) {
        Education newEducation = new Education();

        newEducation.setPrepod(prepodService.getPrepodById(idPerson));
        newEducation.setFormTraining(adapter.getForm());
        newEducation.setLevelTraining(adapter.getLevel());
        newEducation.setVnz(vnZakladService.getVNZakladById(vnZakladService.findIdVNZakladByVnzShortName(adapter.getVnz().getShortName())));
        newEducation.setYearVypusk(adapter.getYear());
        newEducation.setDiplomaNumber(adapter.getDiplomaNumber());
        newEducation.setDiplomaSeries(adapter.getDiplomaSeries());
        newEducation.setDiplomaSpeciality(adapter.getSpeciality());
        newEducation.setDiplomaQualification(adapter.getQualification());
        return newEducation;
    }
}
