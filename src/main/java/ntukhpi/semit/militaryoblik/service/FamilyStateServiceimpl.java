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
    public FamilyState getFamilyState() {
        List<FamilyState> list = familyStateRepository.findAll();
        return !list.isEmpty()?list.get(0):null;
    }
}
