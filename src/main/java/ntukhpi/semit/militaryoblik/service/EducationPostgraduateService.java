package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;

import java.util.List;

public interface EducationPostgraduateService {

    EducationPostgraduate createEducationPostgraduate(EducationPostgraduate educationPostgraduate);

    EducationPostgraduate getEducationPostgraduateById(Long id);

    List<EducationPostgraduate> getAllEducationPostgraduate();

    EducationPostgraduate updateEducationPostgraduate(Long id, EducationPostgraduate updatedEducationPostgraduate);

    void deleteEducationPostgraduate(Long id);
}
