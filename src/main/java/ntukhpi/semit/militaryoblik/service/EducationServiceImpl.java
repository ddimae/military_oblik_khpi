package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Education;
import ntukhpi.semit.militaryoblik.entity.VNZaklad;
import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Prepod;
import ntukhpi.semit.militaryoblik.repository.EducationRepository;
import ntukhpi.semit.militaryoblik.repository.VNZakladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    public Education createEducation(Education document) {
        return educationRepository.save(document);
    }

    @Override
    public Education getEducationById(Long id) {
        return educationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Education> getAllEducation() {
        return educationRepository.findAll();
    }

    @Override
    public List<Education> getAllEducationByPrepod(Prepod prepod) {
        return educationRepository.findAllByPrepod(prepod);
    }

    @Override
    public Education updateEducation(Long id, Education updatedEducation) {
        Education existingEducation = educationRepository.findById(id).orElse(null);
        if (existingEducation != null) {
            updatedEducation.setId(existingEducation.getId());
            return educationRepository.save(updatedEducation);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteEducation(Long id) {
        educationRepository.deleteById(id);
    }

    @Autowired
    private VNZakladRepository vnzakladRepository;

    public List<VNZaklad> getAllVNZ() {
        return vnzakladRepository.findAll();
    }
}
