package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.Document;
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

    public void addEducation(Long idPerson, String form, String level,
                             String vnzName, String year,
                             String diplomaNumber, String diplomaSeries, String specialty, String qualification) {
        Education newEducation = createNewInstance(idPerson, form, level,
                vnzName, year, diplomaNumber, diplomaSeries, specialty, qualification);
        Education newDocumentInDB = educationService.getEducationByKey(newEducation.getPrepod(),
                newEducation.getVnz(),newEducation.getYearVypusk());
        if (newDocumentInDB == null) {
            educationService.createEducation(newEducation);
        } else {
            throw new RuntimeException(MSG_DOUBLE_EDU);
        }


    }

    public void updateEducation(Long idEdu, Long idPersonForUpdate, String form, String level,
                                String vnzShortName, String year, String diplomaNumber,
                                String diplomaSeries, String specialty, String qualification) {
        Education educationUpdate = createNewInstance(idPersonForUpdate, form, level,
                vnzShortName, year, diplomaNumber, diplomaSeries, specialty, qualification);
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

    private Education createNewInstance(Long idPerson, String form, String level,
                                        String vnzShortName, String year, String diplomaNumber,
                                        String diplomaSeries, String specialty, String qualification) {
        Education newEducation = new Education();

        newEducation.setPrepod(prepodService.getPrepodById(idPerson));
        newEducation.setFormTraining(form);
        newEducation.setLevelTraining(level);
        newEducation.setVnz(vnZakladService.getVNZakladById(vnZakladService.findIdVNZakladByVnzShortName(vnzShortName)));
        newEducation.setYearVypusk(year);
        newEducation.setDiplomaNumber(diplomaNumber);
        newEducation.setDiplomaSeries(diplomaSeries);
        newEducation.setDiplomaSpeciality(specialty);
        newEducation.setDiplomaQualification(qualification);
        return newEducation;
    }
}
