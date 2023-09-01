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
}
