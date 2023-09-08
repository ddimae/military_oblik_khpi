package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.EducationPostgraduate;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.EducationPostgraduateRepository;
import ntukhpi.semit.militaryoblik.repository.VNZakladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationPostgraduateServiceImpl implements EducationPostgraduateService {

    private final EducationPostgraduateRepository educationPostgraduateRepository;

    @Autowired
    public EducationPostgraduateServiceImpl(EducationPostgraduateRepository educationPostgraduateRepository) {
        this.educationPostgraduateRepository = educationPostgraduateRepository;
    }

    @Override
    public EducationPostgraduate createEducationPostgraduate(EducationPostgraduate educationPostgraduate) {
        return educationPostgraduateRepository.save(educationPostgraduate);
    }

    @Override
    public EducationPostgraduate getEducationPostgraduateById(Long id) {
        return educationPostgraduateRepository.findById(id).orElse(null);
    }

    @Override
    public List<EducationPostgraduate> getAllEducationPostgraduate() {
        return educationPostgraduateRepository.findAll();
    }

    @Override
    public List<EducationPostgraduate> getAllEducationPostgraduateByPrepod(Prepod prepod) {
        return educationPostgraduateRepository.findAllByPrepod(prepod);
    }

    @Override
    public EducationPostgraduate updateEducationPostgraduate(Long id, EducationPostgraduate updatedEducationPostgraduate) {
        EducationPostgraduate existingEducationPostgraduate = educationPostgraduateRepository.findById(id).orElse(null);
        if (existingEducationPostgraduate != null) {
            updatedEducationPostgraduate.setId(existingEducationPostgraduate.getId());
            return educationPostgraduateRepository.save(updatedEducationPostgraduate);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteEducationPostgraduate(Long id) {
        educationPostgraduateRepository.deleteById(id);
    }

    @Autowired
    private VNZakladRepository vnzakladRepository;

    public List<VNZaklad> getAllVNZ() {
        return vnzakladRepository.findAll();
    }
}
