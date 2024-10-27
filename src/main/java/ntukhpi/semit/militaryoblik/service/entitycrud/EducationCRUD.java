package ntukhpi.semit.militaryoblik.service.entitycrud;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.service.EducationService;
import ntukhpi.semit.militaryoblik.service.PrepodServiceImpl;
import ntukhpi.semit.militaryoblik.service.VNZakladServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationCRUD {

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
        educationService.createEducation(newEducation);

    }

    public void updateEducation(Long idPersonForUpdate, String form, String level,
                                String vnzName, String year, String diplomaNumber,
                                String diplomaSeries, String specialty, String qualification) {
        Education educationUpdate = createNewInstance(idPersonForUpdate, form, level,
                vnzName, year, diplomaNumber, diplomaSeries, specialty, qualification);
        educationService.updateEducation(idPersonForUpdate,educationUpdate);
    }
    public void deleteEducation(Long idDelEducation) {
        educationService.deleteEducation(idDelEducation);
    }




    private Education createNewInstance(Long idPerson, String form, String level,
                                        String vnzName, String year, String diplomaNumber,
                                        String diplomaSeries, String specialty, String qualification) {
        Education newEducation = new Education();

        newEducation.setPrepod(prepodService.getPrepodById(idPerson));
        newEducation.setFormTraining(form);
        newEducation.setLevelTraining(level);
        newEducation.setVnz(vnZakladService.getVNZakladByVnzName(vnzName));
        newEducation.setYearVypusk(year);
        newEducation.setDiplomaNumber(diplomaNumber);
        newEducation.setDiplomaSeries(diplomaSeries);
        newEducation.setDiplomaSpeciality(specialty);
        newEducation.setDiplomaQualification(qualification);
        return newEducation;
    }
}
