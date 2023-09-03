package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.Voenkomat;
import ntukhpi.semit.militaryoblik.repository.VoenkomatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoenkomatServiceImpl implements VoenkomatService {

    private final VoenkomatRepository voenkomatRepository;

    @Autowired
    public VoenkomatServiceImpl(VoenkomatRepository voenkomatRepository) {
        super();
        this.voenkomatRepository = voenkomatRepository;
    }

    @Override
    public List<Voenkomat> getAllVoenkom() {
        return voenkomatRepository.findAll();
    }

    @Override
    public Voenkomat createVoenkomat(Voenkomat voenkomat) {
        return voenkomatRepository.save(voenkomat);
    }

    @Override
    public Voenkomat getVoenkomatById(Long id) {
        return voenkomatRepository.findById(id).orElse(null);
    }

    @Override
    public Voenkomat updateVoenkomat(Long id, Voenkomat updatedVoenkomat) {
        Voenkomat existingVoenkomat = voenkomatRepository.findById(id).orElse(null);
        if (existingVoenkomat != null) {
            updatedVoenkomat.setId(existingVoenkomat.getId());
            return voenkomatRepository.save(updatedVoenkomat);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteVoenkomat(Long id) {
        voenkomatRepository.deleteById(id);
    }
}
