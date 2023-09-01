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
    public List<VZvanie> getAllCountry() {
        return vZvanieRepository.findAll();
    }
}
