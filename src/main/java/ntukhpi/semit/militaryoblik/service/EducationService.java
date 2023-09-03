package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;

import java.util.List;

public interface EducationService {

    Education createEducation(Education document);

    Education getEducationById(Long id);

    List<Education> getAllEducation();

    Education updateEducation(Long id, Education updatedEducation);

    void deleteEducation(Long id);
}
