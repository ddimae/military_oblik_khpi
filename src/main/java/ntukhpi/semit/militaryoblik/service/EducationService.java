package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface EducationService {

    Education createEducation(Education document);

    Education getEducationById(Long id);

    Education getEducationByKey(Prepod p, VNZaklad vnz, String yearEnd);

    List<Education> getAllEducation();

    List<Education> getAllEducationByPrepod(Prepod prepod);

    Education updateEducation(Long id, Education updatedEducation);

    void deleteEducation(Long id);

//   Найти ОДНО образование по PrepodID? А якщо 2 чи 3? метод витягу всіх вище!
//   List<Education> getAllEducationByPrepod(Prepod prepod);
//    Education getEducationByPrepodId(Long id);
}