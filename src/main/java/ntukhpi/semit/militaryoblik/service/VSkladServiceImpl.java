package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VSklad;
import ntukhpi.semit.militaryoblik.repository.VSkladRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VSkladServiceImpl implements VSkladService {

    private final VSkladRepository vSkladRepository;

    @Autowired
    public VSkladServiceImpl(VSkladRepository vSkladRepository) {
        this.vSkladRepository = vSkladRepository;
    }

    @Override
    public VSklad createVSklad(VSklad vSklad) {
        return vSkladRepository.save(vSklad);
    }

    @Override
    public VSklad getVSkladById(Long id) {
        return vSkladRepository.findById(id).orElse(null);
    }

    @Override
    public List<VSklad> getAllVSklad() {
        return vSkladRepository.findAll();
    }

    @Override
    public VSklad updateVSklad(Long id, VSklad updatedVSklad) {
        VSklad existingVSklad = vSkladRepository.findById(id).orElse(null);
        if (existingVSklad != null) {
            updatedVSklad.setId(existingVSklad.getId());
            return vSkladRepository.save(updatedVSklad);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteVSklad(Long id) {
        vSkladRepository.deleteById(id);
    }
}
