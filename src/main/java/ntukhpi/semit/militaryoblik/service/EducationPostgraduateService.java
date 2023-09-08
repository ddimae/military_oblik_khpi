package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;

import java.util.List;

public interface EducationPostgraduateService {

    EducationPostgraduate createEducationPostgraduate(EducationPostgraduate educationPostgraduate);

    EducationPostgraduate getEducationPostgraduateById(Long id);

    List<EducationPostgraduate> getAllEducationPostgraduate();

    List<EducationPostgraduate> getAllEducationPostgraduateByPrepod(Prepod prepod);

    EducationPostgraduate updateEducationPostgraduate(Long id, EducationPostgraduate updatedEducationPostgraduate);

    void deleteEducationPostgraduate(Long id);
}
