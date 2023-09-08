package ntukhpi.semit.militaryoblik.service;

import ntukhpi.semit.militaryoblik.entity.fromasukhpi.Zvanie;
import ntukhpi.semit.militaryoblik.repository.ZvanieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZvanieServiceImpl implements ZvanieService {

    private final ZvanieRepository zvanieRepository;

    @Autowired
    public ZvanieServiceImpl(ZvanieRepository zvanieRepository) {
        this.zvanieRepository = zvanieRepository;
    }

    @Override
    public Zvanie createZvanie(Zvanie zvanie) {
        return zvanieRepository.save(zvanie);
    }

    @Override
    public Zvanie getZvanieById(Long id) {
        return zvanieRepository.findById(id).orElse(null);
    }

    @Override
    public Zvanie getZvanieByName(String zvName) {
        return zvanieRepository.getZvanieByZvanieName(zvName);
    }

    @Override
    public List<Zvanie> getAllZvanie() {
        return zvanieRepository.findAll();
    }

    @Override
    public Zvanie updateZvanie(Long id, Zvanie updatedZvanie) {
        Zvanie existingZvanie = zvanieRepository.findById(id).orElse(null);
        if (existingZvanie != null) {
            updatedZvanie.setId(existingZvanie.getId());
            return zvanieRepository.save(updatedZvanie);
        }
        return null;
    }

    //ToDo change when special conditions was presented
    @Override
    public void deleteZvanie(Long id) {
        zvanieRepository.deleteById(id);
    }

}
