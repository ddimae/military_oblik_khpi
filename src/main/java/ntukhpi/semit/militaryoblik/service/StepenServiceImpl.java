package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Stepen;
import ntukhpi.semit.militaryoblik.repository.StepenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StepenServiceImpl implements StepenService {

    private final StepenRepository stepenRepository;

    @Autowired
    public StepenServiceImpl(StepenRepository stepenRepository) {
        this.stepenRepository = stepenRepository;
    }

    @Override
    public Stepen createStepen(Stepen stepen) {
        return stepenRepository.save(stepen);
    }

    @Override
    public Stepen getStepenById(Long id) {
        return stepenRepository.findById(id).orElse(null);
    }

    @Override
    public Stepen getStepenByName(String stName) {
        return stepenRepository.getStepenByStepenName(stName);
    }

    @Override
    public List<Stepen> getAllStepen() {
        return stepenRepository.findAll();
    }

    @Override
    public Stepen updateStepen(Long id, Stepen updatedStepen) {
        Stepen existingStepen = stepenRepository.findById(id).orElse(null);
        if (existingStepen != null) {
            updatedStepen.setId(existingStepen.getId());
            return stepenRepository.save(updatedStepen);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteStepen(Long id) {
        stepenRepository.deleteById(id);
    }
}
