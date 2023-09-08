package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.VZvanie;
import ntukhpi.semit.militaryoblik.repository.VZvanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VZvanieServiceImpl implements VZvanieService {

    private final VZvanieRepository vZvanieRepository;

    @Autowired
    public VZvanieServiceImpl(VZvanieRepository vZvanieRepository) {
        this.vZvanieRepository = vZvanieRepository;
    }

    @Override
    public VZvanie createVZvanie(VZvanie vZvanie) {
        return vZvanieRepository.save(vZvanie);
    }

    @Override
    public VZvanie getVZvanieById(Long id) {
        return vZvanieRepository.findById(id).orElse(null);
    }

    @Override
    public VZvanie getVzvanieByName(String vzvanieName) {
        return vZvanieRepository.findByZvanieName(vzvanieName);
    }

    @Override
    public List<VZvanie> getAllVZvanie() {
        return vZvanieRepository.findAll();
    }

    @Override
    public VZvanie updateVZvanie(Long id, VZvanie updatedVZvanie) {
        VZvanie existingVZvanie = vZvanieRepository.findById(id).orElse(null);
        if (existingVZvanie != null) {
            updatedVZvanie.setId(existingVZvanie.getId());
            return vZvanieRepository.save(updatedVZvanie);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteVZvanie(Long id) {
        vZvanieRepository.deleteById(id);
    }

    @Override
    public Long getIdVZvanieByName(String vzvanieName) {
        VZvanie vzv = vZvanieRepository.findByZvanieName(vzvanieName);
        return vzv!=null?vzv.getId():0L;
    }
}
