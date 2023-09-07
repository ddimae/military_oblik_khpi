package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface EducationService {

    Education createEducation(Education document);

    Education getEducationById(Long id);

    List<Education> getAllEducation();

    List<Education> getAllEducationByPrepod(Prepod prepod);

    Education updateEducation(Long id, Education updatedEducation);

    void deleteEducation(Long id);
}
