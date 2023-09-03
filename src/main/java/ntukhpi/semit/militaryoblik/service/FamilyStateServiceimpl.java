package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.FamilyState;
import ntukhpi.semit.militaryoblik.repository.FamilyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FamilyStateServiceimpl implements FamilyStateService {

    private final FamilyStateRepository familyStateRepository;

    @Autowired
    public FamilyStateServiceimpl(FamilyStateRepository familyStateRepository) {
        this.familyStateRepository = familyStateRepository;
    }

    @Override
    public FamilyState createFamilyState(FamilyState familyState) {
        return familyStateRepository.save(familyState);
    }

    @Override
    public FamilyState getFamilyStateById(Long id) {
        return familyStateRepository.findById(id).orElse(null);
    }

    @Override
    public FamilyState getFamilyState() {
        List<FamilyState> list = familyStateRepository.findAll();
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public FamilyState updateFamilyState(Long id, FamilyState updatedFamilyState) {
        FamilyState existingFamilyState = familyStateRepository.findById(id).orElse(null);
        if (existingFamilyState != null) {
            updatedFamilyState.setIdFamState(existingFamilyState.getIdFamState());
            return familyStateRepository.save(updatedFamilyState);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteFamilyState(Long id) {
        familyStateRepository.deleteById(id);
    }
}
